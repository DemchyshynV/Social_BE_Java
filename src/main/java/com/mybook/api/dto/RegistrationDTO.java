package com.mybook.api.dto;

import com.mybook.api.models.Sex;
import com.mybook.api.models.Status;
import lombok.Data;

@Data
public class RegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private int age;
    private Sex sex;
}
