package ru.otus.novikov.java.hw11.util;

public interface Mapper<E, D> {
    D mapToDto(E entity);
    E mapToEntity(D dto);
}
