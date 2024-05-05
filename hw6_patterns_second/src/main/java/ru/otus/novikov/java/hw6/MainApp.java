package ru.otus.novikov.java.hw6;

import com.zaxxer.hikari.HikariDataSource;
import ru.otus.novikov.java.hw6.dao.BookDao;
import ru.otus.novikov.java.hw6.dao.BookDaoJdbcProxy;
import ru.otus.novikov.java.hw6.domain.Book;

import javax.sql.DataSource;

public class MainApp {
    public static void main(String[] args){

        BookDao bookDao = new BookDaoJdbcProxy(createSource());
        Book book = Book.builder()
            .id(1)
            .name("newName")
            .author("author")
            .style("style")
            .build();
        Book newBook = Book.builder()
            .id(1)
            .name("newName2")
            .author("author2")
            .style("style2")
            .build();
        bookDao.save(book);
        System.out.println(bookDao.getById(1));
        bookDao.update(newBook);
        System.out.println(bookDao.getById(1));
        bookDao.deleteById(1);
    }

    private static DataSource createSource() {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:h2:mem:test_mem;DB_CLOSE_ON_EXIT=FALSE;INIT=RUNSCRIPT FROM 'classpath:schema.sql';");
        return source;
    }
}
