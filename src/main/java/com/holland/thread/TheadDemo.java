package com.holland.thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TheadDemo {
    /*public static void main(String[] args) throws Exception {
        int cups = Runtime.getRuntime().availableProcessors();

        new MyThead("S1\t\t").start();
        new Thread(new MyRunnable()).start();

        FutureTask<Object> task = new FutureTask<>(new MyCallable());
        new Thread(task).start();

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\tTEST\t" + i);
            }
            return Thread.currentThread().getName() + "\tFINISHED";
        }, Executors.newSingleThreadExecutor());
    }*/

    private static class MyThead extends Thread {
        public MyThead(@NotNull String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + "\tTEST\t" + i);
            }
        }
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\tTEST\t" + i);
            }
        }
    }

    private static class MyCallable implements Callable<Object> {
        @Override
        public Object call() throws Exception {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\tTEST\t" + i);
            }
            return Thread.currentThread().getName() + "\tFINISHED";
        }
    }
}
