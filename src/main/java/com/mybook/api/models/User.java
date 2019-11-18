package com.mybook.api.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User extends BaseEntity {
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String surname;
    private int age;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @OneToOne
    private Avatar avatar;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
