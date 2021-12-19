package com.wbo112.concurrentlock;

import java.util.concurrent.atomic.AtomicBoolean;

public class TASLock  implements  Lock{
    private AtomicBoolean state=new AtomicBoolean(false);
    public void lock(){
        while(state.getAndSet(true)){

        }
    }
    public void unlock(){
        state.set(false);
    }
}
