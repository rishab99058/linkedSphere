package com.linkedsphere.chat_service.entity;

import com.linkedsphere.chat_service.enums.AttachmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageAttachment {

    private AttachmentType type;

    private String storageKey;

    private String url;

    private String fileName;

    private String mimeType;

    private Long size;

    private Integer width;

    private Integer height;
}
