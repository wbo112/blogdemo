package com.wbo112.concurrentlock;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock {


    private AtomicReference<QNode> tail = new AtomicReference<QNode>(new QNode());
    ThreadLocal<QNode> myNode;
    public MCSLock(){

        myNode=ThreadLocal.withInitial(()->new QNode());
    }


    @Override
    public void lock() {
      QNode qNode=myNode.get();
      QNode pred=tail.getAndSet(qNode);
      if(Objects.nonNull(pred)){
          qNode.locked=true;
          pred.next=qNode;
          while(qNode.locked){

          }
      }
    }

    @Override
    public void unlock() {
        QNode qNode=myNode.get();
        if(Objects.isNull(qNode.next)){
            if(tail.compareAndSet(qNode,null)){
                return ;
            }
            while(Objects.isNull(qNode.next)){

            }
        }
        qNode.next.locked=false;
        qNode.next=null;
    }
}
