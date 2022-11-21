package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;


}
