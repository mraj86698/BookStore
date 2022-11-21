package com.example.demo.Service;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.Model.Order;

public interface IOrderService {



	    public List<Order> getAllOrder();

	    public Order getOrderById(Integer orderId);



	    public Order deleteOrder(Integer orderId);



		public Order addOrder(@Valid OrderDTO orderdto);

		/**
		 * Cancel Order Details
		 */
		String cancelOrder(int orderId, int userId);

		/**
		 * To get All Order Details by userId
		 */
		List<Order> getUserOrders(int userId);

		/**
		 * To get All Order Details by userId
		 */
//		List<Order> getUserOrders(String token);




		/**
		 * To get All Order Details by userId
		 */
		//List<Order> getUserOrders(String token, OrderDTO orderdto);

}
