package com.wbo112.concurrentlock;

import java.util.ArrayList;
import java.util.List;

public class TestLock {
    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        TASLock tasLock=new TASLock();
        tLock(tasLock);
        System.out.println("tasLock cost time= "+(System.currentTimeMillis()-startTime));
        startTime=System.currentTimeMillis();
        TTASLock ttasLock=new TTASLock();
        tLock(ttasLock);
        System.out.println("ttasLock cost time= "+(System.currentTimeMillis()-startTime));
        startTime=System.currentTimeMillis();
        BackoffLock backoffLock=new BackoffLock();
        tLock(backoffLock);
        System.out.println("backoffLock cost time= "+(System.currentTimeMillis()-startTime));
        startTime=System.currentTimeMillis();
        ALock aLock=new ALock(100);
        tLock(aLock);
        System.out.println("aLock cost time= "+(System.currentTimeMillis()-startTime));

    }
    public static void tLock(Lock lock){
        List<Thread> threadList=new ArrayList<>();

        for(int i=0;i<50;i++){
          threadList.add( new Thread(()->{
           // for(int j=0;j<100;j++){
                lock.lock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
           // }
            }));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
