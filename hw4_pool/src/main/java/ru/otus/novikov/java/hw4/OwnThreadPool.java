package ru.otus.novikov.java.hw4;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
public class OwnThreadPool {
    private Queue<Runnable> tasks = new LinkedList<>();
    private AtomicBoolean running = new AtomicBoolean(true);
    private ReentrantLock lock = new ReentrantLock(true);
    AtomicInteger ctr = new AtomicInteger();

    public OwnThreadPool(int capacity) {
        for (int i = 0; i < capacity; i++) {
            new Worker().start();
        }
    }

    // Создание рабочего потока
    private class Worker extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            do {
                lock.lock();
                if (!tasks.isEmpty()) {
                    tasks.poll().run();
                    System.out.println(ctr.incrementAndGet());
                }
                lock.unlock();
            } while (running.get());
        }
    }

    /**
     * Передача задачи в пул
     */
    @SneakyThrows
    public void execute(Runnable task) {
        lock.lock();
        if (!running.get()) {
            throw new IllegalArgumentException("Pool was shutdown. Impossible to execute new tasks");
        }
        tasks.add(task);
        lock.unlock();
    }

    /**
     * Завершение приёма задач в пул (с выбросом IllegalStateException при попытке добавить задачу в пул плосле вызова shutdown)
     */
    public void shutdown() {
        this.running.set(false);
    }


}
