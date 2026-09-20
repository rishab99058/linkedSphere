package com.linkedsphere.post_service.controller;

import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/opensearch")
@RequiredArgsConstructor
public class OpenSearchController {

    private final OpenSearchClient openSearchClient;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        try {
            var info = openSearchClient.info();

            return ResponseEntity.ok(
                    "OpenSearch connected successfully. Cluster: "
                            + info.clusterName()
            );

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("OpenSearch connection failed: " + e.getMessage());
        }
    }
}