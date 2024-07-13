package ru.otus.novikov.java.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.novikov.java.hw14.controller.dto.BookDto;
import ru.otus.novikov.java.hw14.dao.BookRepository;
import ru.otus.novikov.java.hw14.dao.domain.Book;
import ru.otus.novikov.java.hw14.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
            .map(book -> BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .style(book.getStyle())
                .build())
            .toList();
    }

    @Override
    public BookDto getBook(Long bookId) {
         Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Not found book for bookId: %s", bookId)));
        return BookDto.builder().id(book.getId()).name(book.getName()).author(book.getAuthor()).style(book.getStyle()).build();
    }

    @Override
    public void createBook(BookDto book) {
        bookRepository.save(Book.builder().name(book.getName()).author(book.getAuthor()).style(book.getStyle()).build());
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
