package ru.otus.novikov.java.hw6;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.novikov.java.hw6.dao.BookDao;
import ru.otus.novikov.java.hw6.dao.BookDaoJdbcProxy;
import ru.otus.novikov.java.hw6.domain.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BookDaoTest {

    private static final DataSource source = createSource();
    private final BookDao bookDao = new BookDaoJdbcProxy(source);

    private final Book book = Book.builder()
        .id(3)
        .name("newName3")
        .author("author3")
        .style("style3")
        .build();

    @BeforeAll
    static void setUp() {
        try {
            Connection connection = source.getConnection();
            connection.createStatement().execute("insert into books (id, name, author, style) values (1, 'name1', 'author1', 'style1');");
            connection.createStatement().execute("insert into books (id, name, author, style) values (2, 'name2', 'author2', 'style2');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBookById() {
        assertThat(bookDao.getById(1)).isEqualTo(getBookById(1));
    }

    @Test
    public void createBook() {
        bookDao.save(book);
        assertThat(getBookById(3)).isEqualTo(book);
    }

    @Test
    public void updateBook() {
        Book bookForUpdate = getBookById(1);
        Book newBook = Book.builder()
            .id(bookForUpdate.getId())
            .name("updatedName")
            .style("updatedStyle")
            .author("updatedAuthor")
            .build();
        bookDao.update(newBook);
        Book updatedBook = getBookById(1);
        assertThat(updatedBook).isEqualTo(newBook);
        assertThat(updatedBook).isNotEqualTo(bookForUpdate);
    }

    @Test
    public void deleteBook() {
        assertThat(getBookById(1)).isNotNull();
        bookDao.deleteById(1);
        assertThatThrownBy(() -> getBookById(1)).isInstanceOf(NoSuchElementException.class);
    }

    private static DataSource createSource() {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:h2:mem:test_mem;DB_CLOSE_ON_EXIT=FALSE;INIT=RUNSCRIPT FROM 'classpath:schema.sql';");
        return source;
    }

    private Book getBookById(int bookId) {
        try {
            Statement statement = source.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from books where id=%s", bookId));
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
        throw new NoSuchElementException();
    }
}
