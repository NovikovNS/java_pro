package ru.otus.novikov.java.hw6.dao;

import ru.otus.novikov.java.hw6.domain.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class BookDaoJdbcProxy implements BookDao {
    private final BookDaoJdbc bookDaoJdbc;

    public BookDaoJdbcProxy(DataSource source) {
        Connection connection = null;
        try {
            connection = source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookDaoJdbc = new BookDaoJdbc(connection);
    }

    @Override
    public Book getById(int bookId) {
        return bookDaoJdbc.getById(bookId);
    }

    @Override
    public void save(Book book) {
        try {
            bookDaoJdbc.getConnection().setAutoCommit(false);
            bookDaoJdbc.save(book);
            bookDaoJdbc.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        try {
            bookDaoJdbc.getConnection().setAutoCommit(false);
            bookDaoJdbc.update(book);
            bookDaoJdbc.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int bookId) {
        try {
            bookDaoJdbc.getConnection().setAutoCommit(false);
            bookDaoJdbc.deleteById(bookId);
            bookDaoJdbc.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
