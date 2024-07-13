package ru.otus.novikov.java.hw14.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("author")
    private String author;
    @Column("style")
    private String style;
}
