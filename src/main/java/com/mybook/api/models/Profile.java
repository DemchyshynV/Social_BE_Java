package com.mybook.api.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
public class Profile extends BaseEntity {
    private String name;
    private String surname;
    private int age;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private String avatar;
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "profile")
    private User user;
}
