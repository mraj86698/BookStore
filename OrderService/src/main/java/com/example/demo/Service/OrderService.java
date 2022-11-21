package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Consumer.MessagingConfig;
import com.example.demo.DTO.Book;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.User;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.Order;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Util.EmailSenderService;
import com.example.demo.Util.TokenUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtility util;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate template;

    /**
     * Add Order Details
     */

	@Override
	public Order addOrder(OrderDTO orderdto) {
//	Optional<Order> order = orderRepo.findById(cartId);
		User user=restTemplate.getForObject("http://localhost:8082/user/getUserById/"+orderdto.getUserId(),User.class);
		System.out.println(user);


		Book book=restTemplate.getForObject("http://localhost:8081/book/getByBook/"+orderdto.getBookId(),Book.class);

        System.out.println(book);
        if (user!=null) {
        	String message="";
        	int orderPrice = book.getPrice() * orderdto.getQuantity();
        	int quantity=book.getQuantity()- orderdto.getQuantity();
        	System.out.println(quantity);
        	//Remove Quantity of Books after Order Placed
            book.setQuantity(quantity);
            restTemplate.put("http://localhost:8081/book/updateQuantity/"+book.getBookId()+"?quantity="+quantity,Book.class);
                Order order = new Order(orderPrice,orderdto);
                System.out.println(order);

                orderRepo.save(order);
                //cartRepo.deleteAll();
                template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderdto);
                log.info("Order record inserted successfully ");
//                mailService.sendEmail(user.getEmail(),
//                        "Your Order was Successfully Placed",
//                        "Order Placed with Given Details \n"
//                                +"Book Name :"+book.getBookName()+"\n"
//                                +"Book Description :"+book.getBookDescription()+"\n"
//                                +"Book Price :"+book.getPrice()+"\n"
//                                +"Book AuthorName:"+book.getAuthorName()+"\n"
//                                +"Order Quantity :"+orderdto.getQuantity()
//                                +"\n"+"Order Price :"+orderPrice);
                //return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), message);
                return order;
        } else {
            throw new BookStoreException("Book or User doesn't exists");
        }
    }
    /**
     * Cancel Order Details
     */

	@Override
    public String cancelOrder(int orderId, int userId) {
        //UserRegistration user = userService.getByToken(token);

            Order order = orderRepo.findById(orderId).orElse(null);
            System.out.println(order);
            User user=restTemplate.getForObject("http://localhost:8082/user/getUserById/"+order.getUserId(),User.class);
    		System.out.println(user);
    		 if (user != null) {
            Book book=restTemplate.getForObject("http://localhost:8081/book/getByBook/"+order.getBookId(),Book.class);

           System.out.println(book);

            if (order != null) {
                order.setCancel(true);
                orderRepo.save(order);
                //To Change Quantity after Calcellation of Order
                book.setQuantity(book.getQuantity() + order.getQuantity());
              //  template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderId);
                //Cancel Mail
                mailService.sendEmail("mraj8669865@gmail.com","Your Order Was Successfully Cancelled","Order was Cancelled\nOrder Id:"+orderId);
//
                return "Order Cancelled";
            }else throw new BookStoreException("Order details not found");
        }else throw new BookStoreException("User does not exists");
    }
    /**
     * To get All Order Details by userId
     */

    @Override
	public List<Order> getUserOrders(int userId) {
    	Order order = orderRepo.findById(userId).orElse(null);
    	User user=restTemplate.getForObject("http://localhost:8082/user/getUserById/"+order.getUserId(),User.class);

       // UserRegistration user = userService.getByToken(token);

        if (user != null) {

            return orderRepo.findAllByUserId(userId);
        } else throw new BookStoreException("Orders for userId not found");
    }


    /**
     * To Get All Order Details
     */
    @Override
	public List<Order> getAllOrder() {
        List<Order> orderList = orderRepo.findAll();
        log.info("ALL order records retrieved successfully");
        return orderList;
    }
    /**
     * To Get Particular Order Details
     */
    @Override
	public Order getOrderById(Integer id) {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new BookStoreException("Order Record doesn't exists");
        } else {
            log.info("Order record retrieved successfully for id " + id);
            return order.get();
        }
    }

    /**
     * To Delete the Order Details
     */
    @Override
	public Order deleteOrder(Integer orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if (order.isEmpty()) {
            throw new BookStoreException("Order Record doesn't exists");
        } else {
            orderRepo.deleteById(orderId);
            log.info("Order record deleted successfully for id " + orderId);
            return order.get();
        }
    }
}
