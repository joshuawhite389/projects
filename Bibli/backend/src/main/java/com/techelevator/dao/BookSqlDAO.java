package com.techelevator.dao;

import com.techelevator.model.BookDTO;
import com.techelevator.model.LogDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookSqlDAO implements BookDAO {

    private JdbcTemplate jdbcTemplate;

    public BookSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addNewBook(String title, String author, Long isbn) {
        int bookId = 0;
        String sql = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?) RETURNING book_id";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, title, author, isbn);

        if (rs.next()) {
            bookId = rs.getInt("book_id");
        }
        return bookId;
    }

    @Override
    public boolean addBookToReadingLog(String username, int bookId) {
        boolean bookAdded = false;
        String sql = "INSERT INTO reading_log (user_id, book_id) VALUES ((SELECT user_id FROM users WHERE username = ?), ?)";
        int count = jdbcTemplate.update(sql, username, bookId);
        bookAdded = (count == 1);
        return bookAdded;
    }

    @Override
    public boolean updateReadingLog(int timeRead, String bookFormat, String notes, boolean isComplete, String username,
                                    int bookId) {
        boolean readingLogUpdated = false;
        String sql = "INSERT INTO reading_log (user_id, book_id, time_read, book_format, notes, is_complete, log_date) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?),  ?, ?,  ?, ?, ?,  NOW() )";
        int count = jdbcTemplate.update(sql, username, bookId, timeRead, bookFormat, notes, isComplete);
        readingLogUpdated = (count == 1);
        return readingLogUpdated;
    }

    @Override
    public boolean bookIsInReadingLog(String title) {
        String sql = "SELECT 1 FROM reading_log WHERE book_id = (SELECT book_id FROM books WHERE title = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, title);
        return rs.next();
    }

    public List<BookDTO> getUserBooks(int userId) {
        List<BookDTO> usersBooks = new ArrayList<>();
        String sql = "SELECT * FROM reading_log " + "JOIN books ON books.book_id = reading_log.book_id "
                + "WHERE user_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        while (rs.next()) {
            usersBooks.add(mapRowToBook(rs));
        }
        return usersBooks;
    }

    public List<LogDTO> getReadingLogs(String family){
        List<LogDTO> logs = new ArrayList<>();
        Map<Integer, Integer> usersLogCount = new LinkedHashMap<>();

        String sql = "SELECT * FROM reading_log as r " +
                "JOIN books as b ON b.book_id = r.book_id " +
                "JOIN users as u ON u.user_id = r.user_id " +
                "WHERE r.user_id IN " +
                " (SELECT r.user_id FROM user_family WHERE family_id = + " +
                "  (SELECT family_id FROM family WHERE family_name = ?))";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, family);

        while (rs.next()) {
            int temp = rs.getInt("user_id");

            if(usersLogCount.containsKey(temp)){
                usersLogCount.replace(temp, usersLogCount.get(temp)+1);
            }else {
                usersLogCount.put(temp, 1);
            }
            logs.add(mapRowToLog(rs, usersLogCount.get(temp)));
        }
        return logs;
    }

    public LogDTO mapRowToLog(SqlRowSet rs, int count) {
        LogDTO log = new LogDTO();
        log.setLog(count);
        log.setUserId(rs.getInt("user_id"));
        log.setUsername(rs.getString("username"));
        log.setBookId(rs.getInt("book_id"));
        log.setBookTitle(rs.getString("title"));
        log.setAuthor(rs.getString("author"));
        log.setTimeRead(rs.getInt("time_read"));
        log.setBookFormat(rs.getString("book_format"));
        log.setNotes(rs.getString("notes"));
        log.setCompleted(rs.getBoolean("is_complete"));
        log.setLogDate(rs.getDate("log_date"));
        return log;
    }


    public BookDTO mapRowToBook(SqlRowSet rs) {
        BookDTO book = new BookDTO();
        book.setAuthor(rs.getString("author"));
        book.setBookId(rs.getInt("book_id"));
        book.setIsbn(rs.getLong("isbn"));
        book.setTimeRead(rs.getInt("time_read"));
        book.setTitle(rs.getString("title"));
        book.setBookFormat(rs.getString("book_format"));
        book.setNotes(rs.getString("notes"));
        book.setCompleted(rs.getBoolean("is_complete"));
        book.setLogDate(rs.getDate("log_date"));
        return book;
    }

}
