package ru.otus.novikov.java.hw14.service;

import ru.otus.novikov.java.hw14.controller.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBook(Long bookId);

    void createBook(BookDto book);

    void deleteBook(Long bookId);
}
