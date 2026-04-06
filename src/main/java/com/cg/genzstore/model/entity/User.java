package com.cg.genzstore.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "users") // MongoDB collection name
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role; // USER / OWNER / ADMIN

}