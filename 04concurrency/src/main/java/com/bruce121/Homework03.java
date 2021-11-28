package com.bruce121;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {

    public static void main(String[] args) {

        Homework03 homework = new Homework03();
        long start = System.currentTimeMillis();

        // method1 通过线程sleep 干等
//        int result = homework.method1();

        // method2 通过使用Countdownlatch
//        int result = homework.method2();

        // method3 通过判断线程状态
//        int result = homework.method3();

        // method4 通过使用FutureTask实现
//        int result = homework.method4();

        // method5 使用线程池执行callable
//        int result = homework.method5();

        // method6 通过wait / notify
        int result = homework.method6();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private int method6() {

        Thread mainThread = Thread.currentThread();

        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        AtomicInteger result = new AtomicInteger();

        threadPool.execute(() -> {
            result.set(MockServer.sum());

            try {
                // 确保主线程已经处于wait状态
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (mainThread) {
                mainThread.notify();
            }
        });

        synchronized (mainThread) {
            try {
                mainThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        return result.get();
    }

    private int method5() {

        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        Future<Integer> future = threadPool.submit(MockServer::sum);

        int result = 0;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private int method4() {

        FutureTask<Integer> newRunnableRutureTask = new FutureTask<>(MockServer::sum);

        Thread thread = new Thread(newRunnableRutureTask);
        thread.start();

        Integer result = 0;

        try {
            result = newRunnableRutureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private int method3() {

        AtomicInteger result = new AtomicInteger();

        Thread thread = new Thread(() -> result.set(MockServer.sum()));
        thread.start();

        while (true) {
            if (thread.getState() == Thread.State.TERMINATED) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result.get();
    }

    private int method2() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        AtomicInteger result = new AtomicInteger();

        Thread thread = new Thread(() -> {

            try {
                result.set(MockServer.sum());
            } finally {
                countDownLatch.countDown();
            }
        });

        thread.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.get();
    }

    private int method1() {

        AtomicInteger result = new AtomicInteger();

        Thread thread = new Thread(() -> result.set(MockServer.sum()));

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.get();
    }

}