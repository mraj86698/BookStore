package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int quantity;
    private String address;
    private int bookId;
    private int userId;
//    private Order order;
//	private Payment payment;

}
