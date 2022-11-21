package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.User;

public interface IUserService {

	  User addUser(UserDTO userDTO);

	    List<User> getAllUsers();

	    String loginUser(String email_id, String password);

	    Object getUserById(String token);

		User updateUser(String token, UserDTO userDTO);

		User getByToken(String token);

		/**
		 * forgot password for User
		 */
		String forgotPassword(String email, String password, String newPassword);

		User findUserById(int userId);





}
