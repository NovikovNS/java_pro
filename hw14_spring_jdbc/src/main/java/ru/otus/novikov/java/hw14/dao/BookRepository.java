package ru.otus.novikov.java.hw14.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.novikov.java.hw14.dao.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
