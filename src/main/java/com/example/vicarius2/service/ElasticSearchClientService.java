package com.example.vicarius2.service;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ElasticSearchClientService {

    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public ElasticSearchClientService(final RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    public String CreateIndex(final Map<String, Object> settings, final Map<String, Object> mappings, final String indexName) throws IOException {
        final CreateIndexRequest request = new CreateIndexRequest(indexName);


        if (settings != null) {
            request.settings(settings);
        }

        if (mappings != null) {
            request.mapping(mappings);
        }

        return restHighLevelClient.indices().create(request, RequestOptions.DEFAULT).toString();
    }

    public String AddDocument(final Map<String, Object> document, final String indexName)  throws IOException {
        final IndexRequest indexRequest = new IndexRequest(indexName).source(document, XContentType.JSON);
        final IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }

    public Map<String, Object> fetchDocument(final String id, final String indexName) throws IOException {
        final SearchRequest searchRequest = new SearchRequest(indexName);
        final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("_id", id));
        searchRequest.source(searchSourceBuilder);
        final SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.getHits().getAt(0).getSourceAsMap();
    }
}
