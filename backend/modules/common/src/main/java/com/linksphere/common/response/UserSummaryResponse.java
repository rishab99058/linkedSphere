package com.linksphere.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse {

    private String userId;

    private String fullName;

    private String profilePictureUrl;

    private String headline;

    private String location;
}
