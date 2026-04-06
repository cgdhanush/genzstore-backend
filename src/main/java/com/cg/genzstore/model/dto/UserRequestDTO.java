package com.cg.genzstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String name;

    @NotBlank
    private String email;
    
    private String password;
    private String role;
}