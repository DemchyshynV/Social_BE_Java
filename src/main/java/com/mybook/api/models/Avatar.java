package com.mybook.api.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
public class Avatar extends BaseEntity {
    private String avatar;
    @OneToOne(mappedBy = "avatar")
    private User user;
}
