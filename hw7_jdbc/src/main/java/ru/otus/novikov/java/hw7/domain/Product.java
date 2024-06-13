package ru.otus.novikov.java.hw7.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.otus.novikov.java.hw7.annotations.MyField;
import ru.otus.novikov.java.hw7.annotations.MyIdField;
import ru.otus.novikov.java.hw7.annotations.MyTable;

@MyTable(title = "products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {

    @MyIdField
    private Long id;

    @MyField
    private String title;

    @MyField
    private Integer price;
}
