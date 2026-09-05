package com.linksphere.user_service.controller;

import com.linksphere.user_service.dto.request.ConnectionRequest;
import com.linksphere.user_service.dto.response.ConnectionResponse;
import com.linksphere.user_service.service.ConnectionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/request")
    public ResponseEntity<ConnectionResponse> sendConnectionRequest(
            @Valid @RequestBody ConnectionRequest request) {
        ConnectionResponse response = connectionService.sendConnectionRequest(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/response")
    public ResponseEntity<ConnectionResponse> decideConnections(
            @Valid @RequestBody ConnectionRequest request) {
        ConnectionResponse response = connectionService.acceptRejectConnectionRequest(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/cancel")
    public ResponseEntity<ConnectionResponse> cancelConnectionRequest(
            @Valid @RequestBody ConnectionRequest request) {
        ConnectionResponse response = connectionService.cancelConnectionRequest(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/requests/list")
    public ResponseEntity<List<ConnectionResponse>> getReceivedConnectionRequests(
            @RequestHeader("X-User-Id") UUID receiverId,
            Pageable pageable) {

        return ResponseEntity.ok(
                connectionService.getReceivedConnectionRequests(
                        receiverId,
                        pageable));
    }

}