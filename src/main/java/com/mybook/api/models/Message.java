package com.mybook.api.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Message extends BaseEntity {
    private String body;
}
