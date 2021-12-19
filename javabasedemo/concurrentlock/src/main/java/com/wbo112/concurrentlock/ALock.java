package com.wbo112.concurrentlock;

import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements Lock{
    private ThreadLocal<Integer>  mySlotIndex=ThreadLocal.withInitial(()->0);
    private AtomicInteger tail;
    private volatile boolean[] flag;
    private int size;
    public ALock(int capacity){
        size=capacity;
        tail=new AtomicInteger(0);
        flag=new boolean[capacity];
        flag[0]=true;
    }
    public void lock(){
        int slot=tail.getAndIncrement()%size;
        mySlotIndex.set(slot);
        while(!flag[slot]){};
    }
    public void unlock(){
        int slot= mySlotIndex.get();
        flag[slot]=false;
        flag[(slot+1)%size]=true;
    }
}
