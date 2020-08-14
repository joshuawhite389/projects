package com.techelevator.controller;

import com.techelevator.model.LogDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.techelevator.dao.BookDAO;
import com.techelevator.model.BookDTO;

import java.util.List;


@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class BookController {

	private BookDAO bookDAO;

	public BookController(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/add-book", method = RequestMethod.POST)
	public BookDTO addUser(@RequestBody BookDTO book) {

		//------NOT UNIQUE NEW BOOK-------------------------------------------------------------------------------------//
		int bookId = bookDAO.addNewBook(book.getTitle(), book.getAuthor(), book.getIsbn());
		bookDAO.addBookToReadingLog(book.getUsername(), bookId);
		return book;
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public void addTimeRead(@RequestBody BookDTO book) {
		//------LOG READING ENTRY DATE AUTO GENERATED----------------------------------------------------------------//
		bookDAO.updateReadingLog(book.getTimeRead(), book.getBookFormat(), book.getNotes(), book.isCompleted(),
				book.getUsername(), book.getBookId());
	}

	@RequestMapping(value = "/show-logs/{family}", method = RequestMethod.GET)
	public List<LogDTO> showLogs(@PathVariable("family") String family) {
		System.out.println(family);
		//------RETURN ALL LOGS FOR ALL BOOKS OF A USER BY ID----------------------------------------------------//
		return bookDAO.getReadingLogs(family);
	}
}
