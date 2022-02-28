package com.holland.thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class TheadDemo {

    static volatile long k = 1;

    /*public static void main(String[] args) throws InterruptedException {

        final long l1 = System.currentTimeMillis();
//        AtomicInteger i = new AtomicInteger(1);
//        LongAdder longAdder = new LongAdder();
        final Thread forLoopThread = new Thread(() -> {
            for (int j = 0; j < Integer.MAX_VALUE / 2; j++) {
//                i.getAndAdd(1);
                k++;
//                longAdder.add(1);
            }
        });
        forLoopThread.setName("forLoopThread");
        forLoopThread.start();

        System.out.println("start--");
        Thread.sleep(1000);
        final long l2 = System.currentTimeMillis();
//        System.out.println("i:: " + i.get());
//        System.out.println("longAdder:: " + longAdder.sum());
        System.out.println("end--" + (l2 - l1) + "ms");

    }*/

    public static void main(String[] args) throws Exception {
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
    }

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
