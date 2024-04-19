package ru.otus.novikov.java.hw4;

import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class OwnThreadPool {
    private Queue<Runnable> tasks = new LinkedList<>();
    private AtomicBoolean running = new AtomicBoolean(true);
    AtomicInteger ctr = new AtomicInteger();

    public OwnThreadPool(int capacity) {
        for (int i = 0; i < capacity; i++) {
            new Worker().start();
        }
    }

    // Создание рабочего потока
    private class Worker extends Thread {
        @Override
        public void run() {
            do {
                Optional<Runnable> taskForExecution = getNext();
                if(taskForExecution.isPresent()) {
                    System.out.println(ctr.incrementAndGet());
                    try {
                        taskForExecution.get().run();
                    } catch (RuntimeException exception) {
                        System.out.printf("There was exception during execution task: %s", exception.getMessage());
                    }
                }
            } while (running.get() || !tasks.isEmpty());
        }
    }

    private synchronized Optional<Runnable> getNext() {
        return Optional.ofNullable(tasks.poll());
    }

    /**
     * Передача задачи в пул
     */
    public synchronized void execute(Runnable task) {
        if (!running.get()) {
            throw new IllegalArgumentException("Pool was shutdown. Impossible to execute new tasks");
        }
        tasks.add(task);
    }

    /**
     * Завершение приёма задач в пул (с выбросом IllegalStateException при попытке добавить задачу в пул плосле вызова shutdown)
     */
    public void shutdown() {
        running.set(false);
    }
}
