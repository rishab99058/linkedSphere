package com.linksphere.user_service.dto.response;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicResponse {

    private UUID userId;
    private String name;
    private String profileImage;
    private String headline;
    private String location;
}
