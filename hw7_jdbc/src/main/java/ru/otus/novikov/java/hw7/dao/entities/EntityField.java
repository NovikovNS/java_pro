package ru.otus.novikov.java.hw7.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityField {
    private Field field;
//    private String actualFieldName;
    private Method getter;
    private Method setter;
}
