package com.mybook.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteLayoutDTO {
    private String name;
    private String surname;
    private String img;
    private boolean massage;
    private boolean friends;

}
