package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.BookDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Model.Book;
import com.example.demo.Service.IBookService;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

	@Autowired
	IBookService bookService;

	/**
	 * Add Book Details using post Mapping
	 *
	 * @param bookDTO
	 * @return
	 */
	@PostMapping("/addBook")
	public ResponseEntity<String> addBookToRepository(@Valid @RequestBody BookDTO bookDTO) {
		Book newBook = bookService.createBook(bookDTO);
		ResponseDTO responseDTO = new ResponseDTO("New Book Added in Book Store", newBook);
		return new ResponseEntity(responseDTO, HttpStatus.CREATED);
	}

	/**
	 * Get All Book Details Using Get mapping
	 *
	 * @return
	 */
	@GetMapping(value = "/getAllBook")
	public ResponseEntity<String> getAllBookData() {
		List<Book> listOfBooks = bookService.getAllBookData();
		ResponseDTO dto = new ResponseDTO("Data retrieved successfully ", listOfBooks);
		return new ResponseEntity(dto, HttpStatus.OK);
	}

	/**
	 * Get Particular Book details for BookId using Get mapping
	 *
	 * @param BookId
	 * @return
	 */
	@GetMapping(value = "/getByBook/{bookId}")
	public Optional<Book> getBookDataById(@PathVariable int bookId) {
		return bookService.getBookDataById(bookId);
//		ResponseDTO dto = new ResponseDTO("Data retrieved successfully by id ", Book);
//		return new ResponseEntity(dto, HttpStatus.OK);
	}

	/**
	 * Delete Book Details for BookId using DeleteMapping
	 *
	 * @param BookId
	 * @return
	 */
	@DeleteMapping("/deleteBook/{bookId}")
	public ResponseEntity<String> deleteRecordById(@PathVariable int bookId) {
		ResponseDTO dto = new ResponseDTO("Book Record deleted successfully", bookService.deleteRecordByToken(bookId));
		return new ResponseEntity(dto, HttpStatus.OK);
	}

	/**
	 * Update Book Details fo BookId Using Put Mapping
	 *
	 * @param BookId
	 * @param bookDTO
	 * @return
	 */
	@PutMapping("/updateBookById/{bookId}")
	public ResponseEntity<String> updateRecordById(@PathVariable Integer bookId, @Valid @RequestBody BookDTO bookDTO) {
		Book updateRecord = bookService.updateRecordById(bookId, bookDTO);
		ResponseDTO dto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
		return new ResponseEntity(dto, HttpStatus.ACCEPTED);
	}
	@PutMapping("/updateQuantity/{bookId}")
	public Book updateQuantity(@PathVariable Integer bookId, @Valid @RequestParam Integer quantity) {
		return bookService.updateQuantity(bookId,quantity);
//		ResponseDTO dto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
//		return new ResponseEntity(dto, HttpStatus.ACCEPTED);
	}

}
