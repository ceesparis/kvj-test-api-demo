package com.example.kvkpliegerdemo3.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationForm {
    private String email;
    private String password;
    private String companyName;
    private String kvkNumber;
    private String location;
}
