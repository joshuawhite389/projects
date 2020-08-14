package com.techelevator.dao;

import com.techelevator.model.BookDTO;
import com.techelevator.model.LogDTO;

import java.util.List;

public interface BookDAO {
	
	int addNewBook(String title, String author, Long isbn);
	
	boolean addBookToReadingLog(String username, int bookId);
	
	boolean updateReadingLog(int timeRead, String bookFormat, String notes, boolean isComplete, String username, int bookId);

	boolean bookIsInReadingLog(String title);

	List<BookDTO> getUserBooks(int userId);

	public List<LogDTO> getReadingLogs(String family);
}
