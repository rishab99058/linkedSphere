package com.linksphere.common.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class ApiErrorResponse implements Serializable {

    private boolean success;

    private int status;

    private String errorCode;

    private String message;

    private List<ApiError> errors;

    @Builder.Default
    private Instant timestamp = Instant.now();

}