package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.Book;
import com.example.demo.DTO.CartDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.DTO.User;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.Cart;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Util.TokenUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService implements ICartService {

	@Autowired
	CartRepository cartRepo;
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TokenUtility util;

	/**
	 * Add Cart to Book Details
	 */
	@Override
	public Cart addItems(CartDTO request) {
		User userResponse = restTemplate.getForObject("http://localhost:8082/user/getUserById/" + request.getUserId(),
				User.class);
		Book bookResponse = restTemplate.getForObject("http://localhost:8081/book/getByBook/" + request.getBookId(),
				Book.class);
		System.out.println(bookResponse);
		System.out.println(userResponse);

		if (bookResponse != null && userResponse != null) {

			int totalPrice = bookResponse.getPrice() * request.getQuantity();
			Cart cart = new Cart(request.getQuantity(), userResponse.getUserId(), bookResponse.getBookId(), totalPrice);

			return cartRepo.save(cart);
		} else {
			throw new BookStoreException("Book or User does not exists");
		}
	}

	/**
	 * To get All Cart Details
	 */
	@Override
	public ResponseDTO getCartDetails() {
		List<Cart> getCartDetails = cartRepo.findAll();
		ResponseDTO dto = new ResponseDTO();
		if (getCartDetails.isEmpty()) {
			String message = " Not found Any Cart details ";
			dto.setMessage(message);
			dto.setData(0);
			return dto;

		} else {
			dto.setMessage("the list of cart items is sucussfully retrived");
			dto.setData(getCartDetails);
			return dto;
		}
	}

	/**
	 * To Get Particular Cart Details
	 */
	@Override
	public Optional<Cart> getCartDetailsById(Integer cartId) {
		Optional<Cart> getCartData = cartRepo.findById(cartId);
		if (getCartData.isPresent()) {
			return getCartData;
		} else {
			throw new BookStoreException(" Didn't find any record for this particular cartId");
		}
	}

	/**
	 * To Delete Cart Details
	 */
	@Override
	public Optional<Cart> deleteCartItemById(Integer cartId) {
		Optional<Cart> deleteData = cartRepo.findById(cartId);
		if (deleteData.isPresent()) {
			cartRepo.deleteById(cartId);
			return deleteData;
		} else {
			throw new BookStoreException(" Did not get any cart for specific cart id ");
		}

	}

	/**
	 * To Update Cart Details
	 */

	@Override
	public Cart updateRecordById(int cartId, CartDTO cartDTO) {
		Optional<Cart> cart = cartRepo.findById(cartId);
		User user = restTemplate.getForObject("http://localhost:8082/user/getUserById/" + cartDTO.getUserId(),User.class);
		Book book = restTemplate.getForObject("http://localhost:8081/book/getByBook/" + cartDTO.getBookId(),Book.class);

		if (cart.isEmpty()) {
			throw new BookStoreException("Cart Record doesn't exists");
		} else {
			if (book != null && user != null) {
				int totalPrice = book.getPrice() * cartDTO.getQuantity();
				Cart newCart = new Cart(cartId, cartDTO.getQuantity(), book.getBookId(), user.getUserId(), totalPrice);
				cartRepo.save(newCart);
				log.info("Cart record updated successfully for id " + cartId);
				return newCart;
			} else {
				throw new BookStoreException("Book or User doesn't exists");
			}
		}
	}

	/**
	 * To update Quantity
	 */

	@Override
	public String updateQuantity(int userId, int cartId, int quantity) {

		Cart cart = cartRepo.findById(cartId).orElse(null);
		User user = restTemplate.getForObject("http://localhost:8082/user/getUserById/" + cart.getUserId(), User.class);
		System.out.println(user);
		Book book = restTemplate.getForObject("http://localhost:8081/book/getByBook/" + cart.getBookId(), Book.class);
		System.out.println(book);
		if (cart != null && user != null && book != null) {
			cart.setQuantity(quantity);
			cart.setTotalPrice(book.getPrice() * quantity);
			cartRepo.save(cart);
			return "Updated with quantity : " + quantity;
		}

		else {
			throw new BookStoreException("Requested quantity is not available");
		}
	}

	/**
	 * To Get Cart Details BY User Id
	 */
	@Override
	public List<Cart> getCartDetailsByUser(String token) {
		int userId = util.decodeToken(token);
		System.out.println(userId);
		List<Cart> userCartList = cartRepo.getCartListByUser(userId);
		if (userCartList.isEmpty()) {
			return null;
		} else
			return userCartList;
	}

}
