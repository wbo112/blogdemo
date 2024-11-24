package com.wbo112.concurrentlock;

import java.util.concurrent.TimeUnit;

public interface Lock {
     void lock();
     void unlock();

     default boolean tryLock(long time, TimeUnit unit){
          return false;
     }
}
