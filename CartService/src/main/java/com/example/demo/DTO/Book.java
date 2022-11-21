package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private int bookId;
    private String  bookName;
    private String  authorName;
    private String  bookDescription;
    private String  bookImage;
    private int price;
    private int quantity;
}