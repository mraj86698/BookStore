package com.example.demo.Model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orderDetails")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private LocalDate orderDate;
    private int  quantity;
    private String address;

   // private String token;
    private int userId;
    private int bookId;
    private boolean cancel=false;
    private int orderPrice;
//    private Order order;
//	private double amount;
//	private String transactionId;
//	private String message;

    public Order(int orderPrice, OrderDTO orderDto) {
    	this.orderDate = LocalDate.now();
        this.quantity=orderDto.getQuantity();
        this.address=orderDto.getAddress();
        this.orderPrice=orderPrice;
        this.userId=orderDto.getUserId();
        this.bookId = orderDto.getBookId();
    }
    public Order(int orderId,int orderPrice, OrderDTO orderDto) {
    	this.orderId=orderId;
    	this.orderDate = LocalDate.now();
        this.quantity=orderDto.getQuantity();
        this.address=orderDto.getAddress();
        this.orderPrice=orderPrice;
        this.userId=orderDto.getUserId();
        this.bookId = orderDto.getBookId();
    }


}
