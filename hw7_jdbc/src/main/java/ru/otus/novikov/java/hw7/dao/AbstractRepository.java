package ru.otus.novikov.java.hw7.dao;

import ru.otus.novikov.java.hw7.annotations.MyField;
import ru.otus.novikov.java.hw7.annotations.MyIdField;
import ru.otus.novikov.java.hw7.annotations.MyTable;
import ru.otus.novikov.java.hw7.dao.entities.EntityField;
import ru.otus.novikov.java.hw7.dao.entities.EntityMetaInfo;
import ru.otus.novikov.java.hw7.exceptions.EntityException;
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
import java.util.concurrent.atomic.AtomicInteger;

public class AbstractRepository<T> {

    private final DataSource dataSource;
    private final Class<T> cls;
    private final String tableName;
    private EntityMetaInfo entityInfo;

    private PreparedStatement psFindAll;
    private PreparedStatement psFindById;
    private PreparedStatement psCreate;
    private PreparedStatement psUpdate;
    private PreparedStatement psDeleteAll;
    private PreparedStatement psDeleteById;

    public AbstractRepository(DataSource dataSource, Class<T> cls) {
        this.dataSource = dataSource;
        this.cls = cls;
        this.tableName = cls.getAnnotation(MyTable.class).title();
        prepareRepository(cls);
    }

    private void prepareRepository(Class<T> cls) {
        try {
            cls.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new PrepareRepositoryException("У сущности отсутствует конструктор по умолчанию");
        }

        List<EntityField> entityFieldsWithoutIdField = Arrays.stream(cls.getDeclaredFields())
            .filter(f -> !f.isAnnotationPresent(MyIdField.class))
            .filter(f -> f.isAnnotationPresent(MyField.class))
            .map(this::buildEntityField).toList();

        if (entityFieldsWithoutIdField.isEmpty()) {
            throw new PrepareRepositoryException("У сущности отсутствует другие поля кроме идентификатора. Неполное описание сущности");
        }

        Field idField = Arrays.stream(cls.getDeclaredFields())
            .filter(f -> f.isAnnotationPresent(MyIdField.class))
            .findFirst().orElseThrow(() -> new PrepareRepositoryException("У сущности отсутствует обязательный идентификатор"));

        EntityField entityFieldWithIdField = buildEntityField(idField);

        entityInfo = new EntityMetaInfo(entityFieldWithIdField, entityFieldsWithoutIdField);

        prepareStatementCreate();
        prepareStatementFindById();
        prepareStatementFindAll();
        prepareStatementUpdate();
        prepareStatementDeleteById();
        prepareStatementDeleteAll();
    }

    private EntityField buildEntityField(Field field) {
        String fieldName = field.getName();
        Method getter;
        Method setter;
        try {
            getter = cls.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        } catch (NoSuchMethodException e) {
            throw new PrepareRepositoryException(String.format("Отсутствует getter для поля %s", fieldName));
        }
        try {
            setter = cls.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), field.getType());
        } catch (NoSuchMethodException e) {
            throw new PrepareRepositoryException(String.format("Отсутствует setter для поля %s", fieldName));
        }
        return new EntityField(field, getter, setter);
    }

    public void create(T entity) {
        try {
            AtomicInteger i = new AtomicInteger(1);
            entityInfo.getFields().forEach(field -> {
                try {
                    psCreate.setObject(i.getAndIncrement(), field.getGetter().invoke(entity));
                } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            psCreate.executeUpdate();
        } catch (Exception e) {
            throw new EntityException(String.format("Ошибка при создании %s", cls.getName()), e.getCause());
        }
    }

    public void update(T entity) {
        try {
            AtomicInteger i = new AtomicInteger(1);
            entityInfo.getFields().forEach(field -> {
                try {
                    psUpdate.setObject(i.getAndIncrement(), field.getGetter().invoke(entity));
                } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            psUpdate.setObject(entityInfo.getFields().size() + 1, entityInfo.getIdField().getGetter().invoke(entity));
            psUpdate.executeUpdate();
        } catch (Exception e) {
            throw new EntityException(String.format("Не удалось обновить %s", cls.getName()), e.getCause());
        }
    }

    public List<T> findAll() {
        try {
            List<T> out = new ArrayList<>();
            ResultSet resultSet = psFindAll.executeQuery();

            while (resultSet.next()) {
                T newObject = cls.getConstructor().newInstance();
                setFieldToNewObject(newObject, entityInfo.getIdField(), resultSet);
                entityInfo.getFields().forEach(field -> {
                    setFieldToNewObject(newObject, field, resultSet);
                });
                out.add(newObject);
            }
            return out;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EntityException(String.format("Ошибка при поиске всех %s", cls.getName()), e.getCause());
        }
    }

    public T findById(Long id) {
        try {
            psFindById.setLong(1, id);
            ResultSet resultSet = psFindById.executeQuery();
            T newObject = cls.getConstructor().newInstance();

            if (resultSet.next()) {
                setFieldToNewObject(newObject, entityInfo.getIdField(), resultSet);
                entityInfo.getFields().forEach(field -> {
                    setFieldToNewObject(newObject, field, resultSet);
                });
            } else {
                throw new EntityNotFoundException(String.format("Не найдён объект по идентификатору %s", id));
            }
            return newObject;
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EntityException(String.format("Ошибка при поиске %s по идентификатору %s", cls.getName(), id), e.getCause());
        }
    }

    private void setFieldToNewObject(T newObject, EntityField entityField, ResultSet resultSet) {
        try {
            entityField.getSetter().invoke(newObject, resultSet.getObject(entityField.getField().getName(), entityField.getField().getType()));
        } catch (IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new EntityException(String.format("Ошибка при установке полей для %s", cls.getName()), e.getCause());
        }
    }

    public void deleteById(Long id) {
        try {
            psDeleteById.setLong(1, id);
            psDeleteById.executeUpdate();
        } catch (SQLException e) {
            throw new EntityException(String.format("Ошибка при удалении %s по идентификатору %s", cls.getName(), id), e.getCause());
        }
    }

    public void deleteAll() {
        try {
            psDeleteAll.executeUpdate();
        } catch (SQLException e) {
            throw new EntityException(String.format("Ошибка при удалении всех %s из таблицы", cls.getName()), e.getCause());
        }
    }

    // INSERT INTO %tableName (%fieldName, %fieldName, ...) VALUES (?, ?, ...)
    private void prepareStatementCreate() {
        StringBuilder query = new StringBuilder("insert into ");
        query.append(tableName).append(" (");

        entityInfo.getFields().forEach(entityField -> {
            query.append(entityField.getField().getName()).append(", ");
        });

        query.setLength(query.length() - 2);
        query.append(") values (");

        query.append("?, ".repeat(entityInfo.getFields().size()));

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
            psFindById = dataSource.getConnection().prepareStatement(String.format("SELECT * FROM %s where %s = ?", tableName,
                entityInfo.getIdField().getField().getName()));
        } catch (SQLException e) {
            throw new PrepareRepositoryException("Проверьте формирование prepareStatement для findById  . Ошибка: " + e.getMessage());
        }
    }

    // UPDATE %tableName SET %fieldName = ?, %fieldName = ? ... where %idFieldName = ?
    private void prepareStatementUpdate() {
        StringBuilder query = new StringBuilder(String.format("UPDATE %s SET ", tableName));

        entityInfo.getFields().forEach(entityField -> {
            query.append(entityField.getField().getName()).append(" = ?, ");
        });

        query.setLength(query.length() - 2);

        query.append(String.format(" WHERE %s = ?", entityInfo.getIdField().getField().getName()));

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
            psDeleteById = dataSource.getConnection().prepareStatement(String.format("DELETE FROM %s where %s = ?", tableName,
                entityInfo.getIdField().getField().getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
