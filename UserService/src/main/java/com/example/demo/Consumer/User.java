package com.example.demo.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Util.EmailSenderService;

@Component
public class User {
	 @Autowired
	    EmailSenderService mailService;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(UserDTO userDto) {

        System.out.println("Message recieved from queue : " + userDto);
        mailService.sendEmail(userDto.getEmail(), "Registered SuccessFully", "Thank you for Registering Book Store App,hii: "
                +userDto.getFirstName());
    }
}
