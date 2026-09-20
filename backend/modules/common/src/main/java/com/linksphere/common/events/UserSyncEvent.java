package com.linksphere.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSyncEvent implements Serializable {
    private String userId;
    private String authId;
    private String fullName;
    private String headline;
    private String location;
    private String industry;
    private String profilePictureUrl;
}
