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
        final CreateIndexRequest request = new CreateIndexRequest(indexName);

        final Map<String, Object> settings = requestDTO.settings();
        final Map<String, Object> mappings = requestDTO.mappings();

        if (settings != null) {
            request.settings(settings);
        }

        if (mappings != null) {
            request.mapping(mappings);
        }

        final CreateIndexResponse response = elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
        return "Index " + indexName + " created with response: " + response.toString();
    }

    @PostMapping("/createDocument")
    public String createDocument(@RequestBody Map<String, Object> document, @RequestParam String indexName) throws IOException {
        final IndexRequest indexRequest = new IndexRequest(indexName).source(document, XContentType.JSON);
        final IndexResponse indexResponse = elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT);
        return "Document created with ID: " + indexResponse.getId();
    }

    @GetMapping("/getDocument")
    public Map<String, Object> getDocument(@RequestParam String id, @RequestParam String indexName) throws IOException {
        final SearchRequest searchRequest = new SearchRequest(indexName);
        final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("_id", id));
        searchRequest.source(searchSourceBuilder);
        final SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.getHits().getAt(0).getSourceAsMap();
    }
}