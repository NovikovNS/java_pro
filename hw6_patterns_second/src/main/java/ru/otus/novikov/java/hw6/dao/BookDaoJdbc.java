package ru.otus.novikov.java.hw6.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.novikov.java.hw6.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Getter
public class BookDaoJdbc implements BookDao {

    private final Connection connection;

    @Override
    public Book getById(int bookId) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from books where id=?");
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Book.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .style(resultSet.getString("style"))
                    .author(resultSet.getString("author"))
                    .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSuchElementException(String.format("Book with id=%s not found", bookId));
    }

    @Override
    public void save(Book book) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into books (id, name, author, style) values (?, ?, ? ,?);");
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getStyle());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {
        try {
            PreparedStatement statement = connection.prepareStatement("update books set name=?, author=?, style=? where id=?;");
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getStyle());
            statement.setInt(4, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int bookId) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from books where id=?");
            statement.setInt(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
