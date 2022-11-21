package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private int userId;

    private int bookId;
    @NotNull(message="Book quantity yet to be provided")
    private int quantity;
}
