package ru.otus.novikov.java.hw3;

public class StreamApp {
    static TaskService taskService = new TaskService();

    public static void main(String[] var) {
        // Получение списка задач по выбранному статусу
        System.out.println(taskService.getTasksByStatus(TaskStatus.OPEN));
        // Проверка наличия задачи с указанным ID
        System.out.println(taskService.hasTaskWithId(1L));
        // Получение списка задач в отсортированном по статусу виде: открыта, в работе, закрыта
        System.out.println(taskService.getSortedByStatusTasks());
        // Подсчет количества задач по определенному статусу
        System.out.println(taskService.tasksCountByStatus(TaskStatus.OPEN));
    }

}
