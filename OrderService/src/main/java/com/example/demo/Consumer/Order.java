package com.example.demo.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.Book;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.User;
import com.example.demo.Util.EmailSenderService;

@Component
public class Order {
	 @Autowired
	    EmailSenderService mailService;
	 @Autowired
	 	RestTemplate restTemplate;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(OrderDTO orderDto) {

        System.out.println("Message recieved from queue : " + orderDto);
        User user=restTemplate.getForObject("http://localhost:8082/user/getUserById/"+orderDto.getUserId(),User.class);
        Book book=restTemplate.getForObject("http://localhost:8081/book/getByBook/"+orderDto.getBookId(),Book.class);
        int orderPrice = book.getPrice() * orderDto.getQuantity();
        mailService.sendEmail(user.getEmail(),
                "Your Order was Successfully Placed",
                "Order Placed with Given Details \n"
                        +"Book Name :"+book.getBookName()+"\n"
                        +"Book Description :"+book.getBookDescription()+"\n"
                        +"Book Price :"+book.getPrice()+"\n"
                        +"Book AuthorName:"+book.getAuthorName()+"\n"
                        +"Order Quantity :"+orderDto.getQuantity()
                        +"\n"+"Order Price :"+orderPrice);
   }


}
