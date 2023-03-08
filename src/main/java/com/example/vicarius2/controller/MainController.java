package com.example.vicarius2.controller;

import com.example.vicarius2.CreateIndexRequestDTO;
import com.example.vicarius2.service.ElasticSearchClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class MainController {

    private final ElasticSearchClientService elasticsearchClient;

    @Autowired
    public MainController(final ElasticSearchClientService elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostMapping("/createIndex")
    @Async(value = "appExecutor")
    public CompletableFuture<ResponseEntity<String>> createIndex(@RequestBody CreateIndexRequestDTO requestDTO, @RequestParam String indexName) throws IOException {

        final String response;
        try {
            response = elasticsearchClient.CreateIndex(requestDTO.settings(), requestDTO.mappings(), indexName);

        } catch (final Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating index"));

        }
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("Index created successfully with response: " + response));

    }

    @PostMapping("/createDocument")
    @Async(value = "appExecutor")
    public CompletableFuture<ResponseEntity<String>> createDocument(@RequestBody Map<String, Object> document, @RequestParam String indexName) throws IOException {

        final String documentId;
        try {
            documentId = elasticsearchClient.AddDocument(document, indexName);
        } catch (final Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Creating document"));

        }
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("Document created with ID: " + documentId));

    }

    @GetMapping("/getDocument")
    @Async(value = "appExecutor")
    public CompletableFuture<ResponseEntity<Object>> getDocument(@RequestParam String id, @RequestParam String indexName) throws IOException {

        final Map<String, Object> result;
        try {
            result = elasticsearchClient.fetchDocument(id, indexName);
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(result));

        } catch (final Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching document"));

        }
    }
}