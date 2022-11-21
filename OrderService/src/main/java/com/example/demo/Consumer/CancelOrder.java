//package com.example.demo.Consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import com.example.demo.Repository.OrderRepository;
//import com.example.demo.Util.EmailSenderService;
//
//@Component
//public class CancelOrder {
//	 @Autowired
//	    EmailSenderService mailService;
//	 @Autowired
//	 	RestTemplate restTemplate;
//	 @Autowired
//	 	OrderRepository orderRepo;
//
//    @RabbitListener(queues = MessagingConfig.QUEUE)
//    public void consumeMessageFromQueue(int orderId) {
//    	System.out.println("Message recieved from queue : " + orderId);
//    	//Order order = orderRepo.findById(orderId).orElse(null);
//    	//User user=restTemplate.getForObject("http://localhost:8082/user/getUserById/"+order.getUserId(),User.class);
//    	//System.out.println(user);
//		//         Book book=restTemplate.getForObject("http://localhost:8081/book/getByBook/"+orderDto.getBookId(),Book.class);
////
//
//
//    	 mailService.sendEmail("mraj8669865@gmail.com","Your Order Was Successfully Cancelled","Order was Cancelled\nOrder Id:"+orderId);
//    }
//
//
//}
