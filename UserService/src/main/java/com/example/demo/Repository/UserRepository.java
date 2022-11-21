package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM user_registration where email=:email_Id", nativeQuery = true)
    public Optional<User> findByEmail(String email_Id);
    @Query(value = "select * from user where email= :email and password = :password", nativeQuery = true)
    List<User> userLogin(String email, String password);
	public User findByUserId(int userId);
}
