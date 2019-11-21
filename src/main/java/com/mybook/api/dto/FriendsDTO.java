package com.mybook.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendsDTO {
    private long id;
    private String avatar;
    private String name;
    private String surname;
}
