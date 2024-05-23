package ru.otus.novikov.java.hw6.dao;

import ru.otus.novikov.java.hw6.domain.Book;

public interface BookDao {
    Book getById(int bookId);
    void save(Book book);
    void update(Book book);
    void deleteById(int bookId);
}
