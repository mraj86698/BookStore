package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query(value = "select * from book where book_name like :%bookName%", nativeQuery = true)
	List<Book> findByBookName(String bookName);

}