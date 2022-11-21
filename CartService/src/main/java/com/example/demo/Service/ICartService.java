package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Model.Cart;

public interface ICartService {
	ResponseDTO getCartDetails();

    Optional<Cart> getCartDetailsById(Integer cartId);

    Optional<Cart> deleteCartItemById(Integer cartId);



    Cart addItems(CartDTO request);



    List<Cart> getCartDetailsByUser(String token);

	//Cart updateQuantity(String token, Integer cartId, int quantity);

//	/**
//	 * To update Quantity
//	 */
//	Cart updateQuantity(int userId, Integer cartId, int quantity);

	/**
	 * To Update Cart Details
	 */
	Cart updateRecordById(int cartId, CartDTO cartDTO);

	/**
	 * To update Quantity
	 */
	String updateQuantity(int userId, int cartId, int quantity);






}
