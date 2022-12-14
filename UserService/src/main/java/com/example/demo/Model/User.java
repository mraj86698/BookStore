package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.demo.DTO.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;

    public User(UserDTO userDTO){
        this.userId=getUserId();
        this.firstName= userDTO.getFirstName();
        this.lastName= userDTO.getLastName();
        this.address= userDTO.getAddress();
        this.email= userDTO.getEmail();
        this.password= userDTO.getPassword();
    }
    public User(int userId,UserDTO userDTO){
        this.userId=userId;
        this.firstName= userDTO.getFirstName();
        this.lastName= userDTO.getLastName();
        this.address= userDTO.getAddress();
        this.email= userDTO.getEmail();
        this.password= userDTO.getPassword();
    }

    public User(String email_id, UserDTO userDTO) {
        this.email=email_id;
        this.firstName= userDTO.getFirstName();
        this.lastName= userDTO.getLastName();
        this.address=userDTO.getAddress();
        this.password= userDTO.getPassword();
    }

    public void updateUser(UserDTO user) {
        this.firstName= user.getFirstName();
        this.lastName=user.getLastName();
        this.email= user.getEmail();
        this.address=user.getAddress();
        this.password=user.getPassword();
    }
}
