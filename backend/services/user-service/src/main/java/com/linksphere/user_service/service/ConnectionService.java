package com.linksphere.user_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.linksphere.user_service.dto.request.ConnectionRequest;
import com.linksphere.user_service.dto.response.ConnectionResponse;

public interface ConnectionService {

    ConnectionResponse sendConnectionRequest(ConnectionRequest request);

    ConnectionResponse acceptRejectConnectionRequest(ConnectionRequest request);

    ConnectionResponse cancelConnectionRequest(ConnectionRequest request);

    List<ConnectionResponse> getReceivedConnectionRequests(
            UUID receiverId,
            Pageable pageable);

}
