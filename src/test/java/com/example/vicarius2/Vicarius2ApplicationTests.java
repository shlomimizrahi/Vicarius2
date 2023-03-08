package com.example.vicarius2;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private final String indexName;

    public MyControllerTest() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        this.indexName = UUID.randomUUID().toString().replace("_", "");
    }

    @Value("${testsuite.webserver.port}")
    private int port;

    private String getUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    @DisplayName("Test Create Index API")
    @Order(1)
    void testCreateIndexAPI() {
        // Prepare request body
        CreateIndexRequestDTO requestDTO = new CreateIndexRequestDTO(new HashMap<>(), new HashMap<>());


        // Send request to API
        ResponseEntity<String> response = restTemplate.postForEntity(
                getUrl("/createIndex?indexName="+ indexName), requestDTO, String.class);

        // Verify response
        assertAll("Verify response",
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("Index created successfully"))
        );
    }

    @Test
    @DisplayName("Test Create Document API")
    @Order(2)
    void testCreateDocumentAPI() {
        // Prepare request body
        Map<String, Object> document = new HashMap<>();
        document.put("field1", "value1");
        document.put("field2", "value2");

        // Send request to API
        ResponseEntity<String> response = restTemplate.postForEntity(
                getUrl("/createDocument?indexName=" + indexName), document, String.class);

        // Verify response
        assertAll("Verify response",
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("Document created with ID"))
        );
    }

    @Test
    @DisplayName("Test Get Document API")
    @Order(3)
    void testGetDocumentAPI() {
        // Create a document
        Map<String, Object> document = new HashMap<>();
        document.put("field1", "value1");
        document.put("field2", "value2");
        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                getUrl("/createDocument?indexName=" + indexName), document, String.class);
        String documentId = Objects.requireNonNull(createResponse.getBody()).split(":")[1].trim();

        // Send request to API
        ResponseEntity<Object> response = restTemplate.getForEntity(
                getUrl("/getDocument?indexName=" + indexName + "&id=" + documentId), Object.class);

        // Verify response
        assertAll("Verify response",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()));

    }
}
