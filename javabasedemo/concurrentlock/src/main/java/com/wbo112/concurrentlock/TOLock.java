package com.wbo112.concurrentlock;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TOLock implements Lock {
    static QNode AVAILABLE = new QNode();
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public TOLock() {
        tail = new AtomicReference<>();
        myNode = ThreadLocal.withInitial(() -> new QNode());
    }

    @Override
    public void lock() {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        long startTime = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        QNode qNode = new QNode();
        myNode.set(qNode);
        qNode.pred = null;
        QNode myPred = tail.getAndSet(qNode);
        if (Objects.isNull(myPred) || myPred.pred == AVAILABLE) {
            return true;
        }
        while (System.currentTimeMillis() - startTime < patience) {
            QNode predPred = myPred.pred;
            if (predPred == AVAILABLE) {
                return true;
            } else if (Objects.nonNull(predPred)) {
                myPred = predPred;
            }

        }
        if (!tail.compareAndSet(qNode, myPred)) {
            qNode.pred = myPred;
        }
        return false;


    }

    @Override
    public void unlock() {
        QNode qNode = myNode.get();
        if (!tail.compareAndSet(qNode, null)) {
            qNode.pred = AVAILABLE;
        }
    }

    private static class QNode {
        public QNode pred = null;
    }
}
