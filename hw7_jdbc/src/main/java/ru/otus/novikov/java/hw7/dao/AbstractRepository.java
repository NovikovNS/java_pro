package ru.otus.novikov.java.hw7.dao;

import ru.otus.novikov.java.hw7.annotations.MyField;
import ru.otus.novikov.java.hw7.annotations.MyIdField;
import ru.otus.novikov.java.hw7.annotations.MyTable;
import ru.otus.novikov.java.hw7.exceptions.EntityNotFoundException;
import ru.otus.novikov.java.hw7.exceptions.PrepareRepositoryException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractRepository<T> {

    DataSource dataSource;
    Class<T> cls;
    String tableName;

    private PreparedStatement psFindAll;
    private PreparedStatement psFindById;
    private PreparedStatement psCreate;
    private PreparedStatement psUpdate;
    private PreparedStatement psDeleteAll;
    private PreparedStatement psDeleteById;

    private List<Field> fieldsWithoutIdField;
    private List<Field> allFields;
    private Field idField;

    private Map<String, Method> fieldNameToGetterMethodMap;
    private Map<String, Method> fieldNameToSetterMethodMap;


    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;
        this.cls = cls;
        this.tableName = cls.getAnnotation(MyTable.class).title();
        prepareRepository(cls);
    }

    private void prepareRepository(Class<T> cls) {
        fieldsWithoutIdField = Arrays.stream(cls.getDeclaredFields())
            .filter(f -> !f.isAnnotationPresent(MyIdField.class))
            .filter(f -> f.isAnnotationPresent(MyField.class))
            .collect(Collectors.toList());

        if (fieldsWithoutIdField.isEmpty()) {
            throw new PrepareRepositoryException("У сущности отсутствует другие поля кроме идентификатора. Неполное описание сущности");
        }

        allFields = Arrays.stream(cls.getDeclaredFields()).collect(Collectors.toList());

        idField = Arrays.stream(cls.getDeclaredFields())
            .filter(f -> f.isAnnotationPresent(MyIdField.class))
            .findFirst().orElseThrow(() -> new PrepareRepositoryException("У сущности отсутствует обязательный идентификатор"));

        fieldNameToGetterMethodMap = allFields.stream().collect(Collectors.toMap(Field::getName, (field) -> {
            String fieldName = field.getName();
            try {
                return cls.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }));

        if (fieldNameToGetterMethodMap.size() != allFields.size()) {
            throw new PrepareRepositoryException("Количество полей сущности не соответствует количеству методов getter");
        }

        fieldNameToSetterMethodMap = allFields.stream().collect(Collectors.toMap(Field::getName, (field) -> {
            String fieldName = field.getName();
            try {
                return cls.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }));

        if (fieldNameToSetterMethodMap.size() != allFields.size()) {
            throw new PrepareRepositoryException("Количество полей сущности не соответствует количеству методов setter");
        }

        prepareStatementCreate();
        prepareStatementFindById();
        prepareStatementFindAll();
        prepareStatementUpdate();
        prepareStatementDeleteById();
        prepareStatementDeleteAll();
    }

    public void create(T entity) {
        try {
            for (int i = 0; i < fieldsWithoutIdField.size(); i++) {
                Field field = fieldsWithoutIdField.get(i);
                psCreate.setObject(i + 1, fieldNameToGetterMethodMap.get(field.getName()).invoke(entity));
            }
            psCreate.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public void update(T entity) {
        try {
            for (int i = 0; i < fieldsWithoutIdField.size(); i++) {
                Field field = fieldsWithoutIdField.get(i);
                psUpdate.setObject(i + 1, fieldNameToGetterMethodMap.get(field.getName()).invoke(entity));
            }
            psUpdate.setObject(fieldsWithoutIdField.size() + 1, fieldNameToGetterMethodMap.get(idField.getName()).invoke(entity));
            psUpdate.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public List<T> findAll() {
        try {
            List<T> out = new ArrayList<>();
            ResultSet resultSet = psFindAll.executeQuery();

            while (resultSet.next()) {
                T newObject = cls.getConstructor().newInstance();
                allFields.forEach(field -> {
                    try {
                        Method method = fieldNameToSetterMethodMap.get(field.getName());
                        method.invoke(newObject, resultSet.getObject(field.getName(), field.getType()));
                    } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
                        e.printStackTrace();
                    }
                });
                out.add(newObject);
            }
            return out;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public T findById(Long id) {
        try {
            psFindById.setLong(1, id);
            ResultSet resultSet = psFindById.executeQuery();
            T newObject = cls.getConstructor().newInstance();

            if (resultSet.next()) {
                allFields.forEach(field -> {
                    try {
                        Method method = fieldNameToSetterMethodMap.get(field.getName());
                        method.invoke(newObject, resultSet.getObject(field.getName(), field.getType()));
                    } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                throw new EntityNotFoundException(String.format("Не найдён объект по идентификатору %s", id));
            }
            return newObject;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteById(Long id) {
        try {
            psDeleteById.setLong(1, id);
            psDeleteById.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public void deleteAll() {
        try {
            psDeleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // INSERT INTO %tableName (%fieldName, %fieldName, ...) VALUES (?, ?, ...)
    private void prepareStatementCreate() {
        StringBuilder query = new StringBuilder("insert into ");
        query.append(tableName).append(" (");

        for (Field f : fieldsWithoutIdField) {
            query.append(f.getName()).append(", ");
        }

        query.setLength(query.length() - 2);
        query.append(") values (");

        for (Field f : fieldsWithoutIdField) {
            query.append("?, ");
        }

        query.setLength(query.length() - 2);
        query.append(");");
        try {
            psCreate = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new PrepareRepositoryException("Проверьте формирование prepareStatement для create. Ошибка: " + e.getMessage());
        }
    }

    // SELECT * FROM %tableName
    private void prepareStatementFindAll() {
        try {
            psFindAll = dataSource.getConnection().prepareStatement(String.format("SELECT * FROM %s", tableName));
        } catch (SQLException e) {
            throw new PrepareRepositoryException("Проверьте формирование prepareStatement для findAll. Ошибка: " + e.getMessage());
        }
    }

    // SELECT * FROM %tableName where %idFieldName = %s
    private void prepareStatementFindById() {
        try {
            psFindById = dataSource.getConnection().prepareStatement(String.format("SELECT * FROM %s where %s = ?", tableName, idField.getName()));
        } catch (SQLException e) {
            throw new PrepareRepositoryException("Проверьте формирование prepareStatement для findById  . Ошибка: " + e.getMessage());
        }
    }

    // UPDATE %tableName SET %fieldName = ?, %fieldName = ? ... where %idFieldName = ?
    private void prepareStatementUpdate() {
        StringBuilder query = new StringBuilder(String.format("UPDATE %s SET ", tableName));

        for (Field f : fieldsWithoutIdField) {
            query.append(f.getName()).append(" = ?, ");
        }

        query.setLength(query.length() - 2);

        query.append(String.format(" WHERE %s = ?", idField.getName()));

        try {
            psUpdate = dataSource.getConnection().prepareStatement(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE FROM %tableName
    private void prepareStatementDeleteAll() {
        try {
            psDeleteAll = dataSource.getConnection().prepareStatement(String.format("DELETE FROM %s", tableName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE FROM %tableName where %idFieldName = %s
    private void prepareStatementDeleteById() {
        try {
            psDeleteById = dataSource.getConnection().prepareStatement(String.format("DELETE FROM %s where %s = ?", tableName, idField.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
