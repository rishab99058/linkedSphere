package com.linksphere.common.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class ApiPageResponse<T> implements Serializable {

    private boolean success;

    private String message;

    private List<T> data;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    @Builder.Default
    private Instant timestamp = Instant.now();

}