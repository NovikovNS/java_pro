package ru.otus.novikov.java.hw4;

public class MainApplication {

    public static void main(String[] args) {
        OwnThreadPool ownPool = new OwnThreadPool(5);
        for (int i = 0; i < 50; i++) {
            ownPool.execute(new Task());
        }
        ownPool.shutdown();
    }

    private static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
