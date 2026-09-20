package com.linkedsphere.post_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDocument {

    @Id
    private String id;

    private String fullName;

    private String headline;

    private String location;

    private String industry;

    private String profilePictureUrl;
}
