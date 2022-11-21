package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Cart {


	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int cartId;

    private int userId;

    private int bookId;
    private int quantity;
    private int totalPrice;

    public Cart(int cartId, int quantity, int userId,int bookId,int totalPrice) {
        super();
        this.cartId = cartId;
        this.quantity = quantity;
        this.userId = userId;
        this.bookId = bookId;
        this.totalPrice=totalPrice;
    }




    public Cart( int quantity, int userId, int bookId,int totalPrice) {
        super();
        this.quantity = quantity;
        this.userId = userId;
        this.bookId = bookId;
        this.totalPrice=totalPrice;
    }




}
