package com.wbo112.concurrentlock;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock {
    private AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        myNode = ThreadLocal.withInitial(() -> new QNode());
        myPred = ThreadLocal.withInitial(() -> null);
    }

    public void lock() {
        QNode qNode = myNode.get();
        qNode.locked = true;
        QNode pred = tail.getAndSet(qNode);
        myPred.set(pred);
        while (pred.locked) {
        }
    }

    public void lock(long   time) {
        long start=System.currentTimeMillis();
        QNode qNode = myNode.get();
        qNode.locked = true;
        QNode pred = tail.getAndSet(qNode);
        myPred.set(pred);
        while (pred.locked) {
            //超时
            if((System.currentTimeMillis()-start)>time){
                return;
            }
        }
    }

    public void unlock() {
        QNode qNode = myNode.get();
        qNode.locked = false;
        myNode.set(myPred.get());
    }

}
