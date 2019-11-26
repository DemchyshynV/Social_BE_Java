package com.mybook.api.models;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Friends extends BaseEntity{
    private long friend_id;
    private long profile_id;
}
