package com.wbo112.jni;


import java.util.*;
import java.util.concurrent.*;

public class JNIDemo {
    public native String callHello();

    public native boolean sendMsg(String str);


    //保存JNI返回的结果
    private BlockingQueue<Entry> queue = new ArrayBlockingQueue<Entry>(10);

    //处理jni返回的任务 线程池
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {

        //加载so文件 libJNIDemo.so
        System.loadLibrary("JNIDemo");


        //System.load();   也可以用这种方式，参数是so文件的全路径
        JNIDemo jniDemo = new JNIDemo();

        //保存需要提交的任务
        Set<String> sets = new ConcurrentSkipListSet<>();

        String str;
        for (int i = 0; i < 10; i++) {
            str = UUID.randomUUID().toString();
            sets.add(str);
            System.out.println("commit task :" + str);

            //在这里会调用jni 提交任务，jni中会新启动一个线程，异步去执行任务，执行完了会调用putEntry方法，添加到需要回调的任务列表中
            jniDemo.sendStr(str);
        }

        Thread thread = new Thread(() -> {
            while (true) {

                try {
                    Entry entry = jniDemo.queue.take();
                    sets.remove(entry.str);

                    //在线程中中，进行回调通知，比如向调用方发送任务处理结果
                    jniDemo.executor.execute(() -> System.out.println(entry.str + "process finish"));

                    //所有任务进行回调后，结束添加回调任务线程，同时关闭线程池
                    if (sets.isEmpty()) {
                        jniDemo.executor.shutdown();
                        break;
                    }
                } catch (InterruptedException e) {
                    System.out.println("thread interrupt ");

                    jniDemo.executor.shutdown();
                    break;
                }

            }
        });
        thread.start();

        //也可以通过这种方式，比如在其他地方在中断回调任务的添加执行，关闭线程池
        //thread.interrupt();


    }

    public boolean sendStr(String str) {
        return sendMsg(str);
    }


    public void putEntry(Entry entry) {
        queue.add(entry);
    }

    private static class Entry {

        //表示一个任务
        private String str;

        //表示任务处理结果
        private String result;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "str='" + str + '\'' +
                    ", result='" + result + '\'' +
                    '}';
        }
    }

}
