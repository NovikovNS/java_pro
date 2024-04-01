package ru.otus.novikov.java.hw3;

import java.util.Comparator;
import java.util.List;

public class TaskService {
    static List<Task> tasksRepo = List.of(
        Task.builder().id(1L).status(TaskStatus.OPEN).name("OPEN1").build(),
        Task.builder().id(2L).status(TaskStatus.IN_PROGRESS).name("IN_PROGRESS1").build(),
        Task.builder().id(3L).status(TaskStatus.CLOSED).name("CLOSED1").build(),
        Task.builder().id(4L).status(TaskStatus.OPEN).name("OPEN2").build(),
        Task.builder().id(5L).status(TaskStatus.IN_PROGRESS).name("IN_PROGRESS2").build(),
        Task.builder().id(6L).status(TaskStatus.CLOSED).name("CLOSED2").build()
    );

    /**
     * Получение списка задач по выбранному статусу
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return tasksRepo.stream().filter(task -> task.status == status).toList();
    }

    /**
     * Проверка наличия задачи с указанным ID
     */
    public boolean hasTaskWithId(long id) {
        return tasksRepo.stream().anyMatch(task -> task.id == id);
    }


    /**
     * Получение списка задач в отсортированном по статусу виде: открыта, в работе, закрыта
     * (можете выбирать любой статус и любой порядок, главное чтобы было 3 разных статуса)
     */
    public List<Task> getSortedByStatusTasks() {
        return tasksRepo.stream().sorted(Comparator.comparing(task -> task.status)).toList();
    }

    /**
     * Подсчет количества задач по определенному статусу
     */
    public Long tasksCountByStatus(TaskStatus status) {
        return tasksRepo.stream().filter(task -> task.status == status).count();
    }
}
