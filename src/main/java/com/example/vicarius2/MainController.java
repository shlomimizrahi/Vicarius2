package com.example.vicarius2;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class MainController {

    private final RestHighLevelClient elasticsearchClient;

    @Autowired
    public MainController(RestHighLevelClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostMapping("/createIndex")
    public String createIndex(@RequestBody CreateIndexRequestDTO requestDTO, @RequestParam String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        if (requestDTO.getSettings() != null) {
            request.settings(requestDTO.getSettings());
        }

        if (requestDTO.getMappings() != null) {
            request.mapping(requestDTO.getMappings());
        }

        CreateIndexResponse response = elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
        return "Index " + indexName + " created with response: " + response.toString();
    }

    @PostMapping("/createDocument")
    public String createDocument(@RequestBody Map<String, Object> document, @RequestParam String indexName) throws IOException {
        IndexRequest indexRequest = new IndexRequest(indexName).source(document, XContentType.JSON);
        IndexResponse indexResponse = elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT);
        return "Document created with ID: " + indexResponse.getId();
    }

    @GetMapping("/getDocument")
    public Map<String, Object> getDocument(@RequestParam String id, @RequestParam String indexName) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("_id", id));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.getHits().getAt(0).getSourceAsMap();
    }
}
