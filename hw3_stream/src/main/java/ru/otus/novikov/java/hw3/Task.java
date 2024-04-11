package ru.otus.novikov.java.hw3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Задача
 */
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    /**
     * ID
     */
    public long id;

    /**
     * Название задачи
     */
    public String name;

    /**
     * Статус задачи
     */
    public TaskStatus status;
}
