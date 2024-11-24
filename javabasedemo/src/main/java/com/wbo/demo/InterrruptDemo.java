package com.wbo.demo;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InterrruptDemo {

    public static void main(String[] args) throws InterruptedException {

        for(int i=0;i<10000000;i++){
            a();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + LocalDateTime.now());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        Thread.sleep(1000);
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " 线程池当前没有完全关闭");
        }
        System.out.println(Thread.currentThread().getName() + " 线程池已经完全关闭");
    }
    public static void a(){
        System.out.println("aaa");
    }
}
