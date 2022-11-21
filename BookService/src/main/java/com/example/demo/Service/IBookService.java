package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Model.Book;

public interface IBookService {
	Book createBook(BookDTO bookDTO);

	Optional<Book> getBookDataById(int bookId);

	List<Book> getAllBookData();

	Book updateRecordById(Integer bookId, BookDTO bookDTO);

	Object deleteRecordByToken(int bookId);


	Book updateQuantity(Integer bookId, int quantity);



}
