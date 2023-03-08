package com.example.vicarius2.controller;

import com.example.vicarius2.CreateIndexRequestDTO;
import com.example.vicarius2.service.ElasticSearchClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class MainController {

    private final ElasticSearchClientService elasticsearchClient;

    @Autowired
    public MainController(ElasticSearchClientService elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostMapping("/createIndex")
    public String createIndex(@RequestBody CreateIndexRequestDTO requestDTO, @RequestParam String indexName) throws IOException {
        final String response = elasticsearchClient.CreateIndex(requestDTO.settings(),requestDTO.mappings(), indexName);
        return "Index " + indexName + " created with response: " + response;
    }

    @PostMapping("/createDocument")
    public String createDocument(@RequestBody Map<String, Object> document, @RequestParam String indexName) throws IOException {
        final String documentId = elasticsearchClient.AddDocument(document, indexName);
        return "Document created with ID: " + documentId;
    }

    @GetMapping("/getDocument")
    public Map<String, Object> getDocument(@RequestParam String id, @RequestParam String indexName) throws IOException {
        return elasticsearchClient.fetchDocument(id, indexName);
    }
}