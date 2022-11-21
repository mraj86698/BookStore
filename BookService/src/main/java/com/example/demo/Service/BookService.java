package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.BookDTO;
import com.example.demo.Exception.BookStoreException;
import com.example.demo.Model.Book;
import com.example.demo.Repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepo;

	/**
	 * Add New Book Data
	 */
	@Override
	public Book createBook(BookDTO bookDTO) {
		Book newBook = new Book(bookDTO);
		return bookRepo.save(newBook);

	}

	/**
	 * Get Particular Book Data By BookId
	 */
	@Override
	public Optional<Book> getBookDataById(int bookId) {
		Optional<Book> getBook = bookRepo.findById(bookId);
		if (getBook.isEmpty()) {
			throw new BookStoreException("Book Store Details for id not found");
		}
		return getBook;

	}

	/**
	 * Get All Book Data
	 */

	@Override
	public List<Book> getAllBookData() {
		List<Book> getBooks = bookRepo.findAll();
		return getBooks;
	}

	/**
	 * Update Book data by Book Id
	 */
	@Override
	public Book updateRecordById(Integer bookId, BookDTO bookDTO) {

		Optional<Book> updateBook = bookRepo.findById(bookId);
		if (updateBook.isEmpty()) {
			throw new BookStoreException("Book record does not found");
		} else {
			Book updateUser = new Book(bookId, bookDTO);
			bookRepo.save(updateUser);
			return updateUser;

		}
	}

	@Override
	public Book updateQuantity(Integer bookId, int quantity) {

		Optional<Book> book = bookRepo.findById(bookId);
		if (book.isEmpty()) {
			throw new BookStoreException("Book record does not found");
		} else {
			book.get().setQuantity(quantity);
            bookRepo.save(book.get());
            log.info("Quantity for book record updated successfully for id "+bookId);
            return book.get();
		}
	}

	/**
	 * Delete Book data By BookId
	 */
	@Override
	public String deleteRecordByToken(int bookId) {
		Optional<Book> newBook = bookRepo.findById(bookId);
		if (newBook.isEmpty()) {
			throw new BookStoreException("Book record does not found");
		} else {
			bookRepo.deleteById(bookId);
		}
		return "data deleted succesfull";
	}

}