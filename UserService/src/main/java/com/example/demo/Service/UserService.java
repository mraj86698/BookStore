package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Consumer.MessagingConfig;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Util.EmailSenderService;
import com.example.demo.Util.TokenUtility;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtility util;
    @Autowired
    private RabbitTemplate template;

    /**
     * Add User Details
     */
    @Override
    public User addUser(UserDTO userDTO) {
        User user= new User(userDTO);
        userRepository.save(user);
       // String token = util.createToken(user.getUserId());
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, user);
//        mailService.sendEmail(user.getEmail(), "Registered SuccessFully", "Thank you for Registering Book Store App,hii: "
//                +user.getFirstName()+"Please Click here to get data-> "
//                +"http://localhost:8081/user/getBy/"+token);


        return user;
    }
    @Override
	public User findUserById(int userId) {
		 return userRepository.findByUserId(userId);

	    }
    /**
     * To get User Details
     */
    @Override
    public List<User> getAllUsers() {
        List<User> getUsers= userRepository.findAll();
        return getUsers;
    }

    /**
     * Get Particular User By Id Using Token
     */
    @Override
    public Object getUserById(String token) {
        int id=util.decodeToken(token);
        Optional<User> getUser=userRepository.findById(id);
        if(getUser.isEmpty()){
            throw new BookStoreException("Record for provided userId is not found");
        }
        else {
            mailService.sendEmail("mraj8669865@gmail.com", "This is Your Token For Decode Your data", "Get your data with this token, hii: "
                    +getUser.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8080/user/getBy/"+token);
            return getUser;
        }

    }
   /**
    * Get Login for User
    */
    @Override
    public String loginUser(String email, String password) {
     Optional<User> login = userRepository.findByEmail(email);
     if(login.isPresent()){
         String pass = login.get().getPassword();
         if(login.get().getPassword().equals(password)){
             return "User Login successfully";
         }

         else {
             return "Wrong Password";
         }
     }
        return "User not found";
    }


    /**
     * forgot password for User
     */

    @Override
    public String forgotPassword(String email, String oldpassword, String newPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String password = user.get().getPassword();
            if (password.equals(oldpassword)) {
                User user1 = user.get();
                user1.setPassword(newPassword);
                userRepository.save(user1);
                mailService.sendEmail(user1.getEmail(), "Password Reset mail", "User password reset successfully");
                return "Password updated successfully";
            }else throw new BookStoreException("Please enter correct password");
        }
        return "User not found";
    }

    @Override
    public User getByToken(String token) {
        int id = util.decodeToken(token);
        return userRepository.findById(id).orElseThrow(() -> new BookStoreException("User data not found"));
    }
	@Override
    public User updateUser(String token, UserDTO userDTO) {

        User user=this.getByToken(token);
        user.updateUser(userDTO);
        mailService.sendEmail(user.getEmail(), "Update mail", "User data updated successfully");
        return userRepository.save(user);

    }










}