package com.wbo112.concurrentlock;

public class MyAtomicBoolean {
    private boolean  value=false;

    public MyAtomicBoolean(boolean b) {
        value=b;
    }

    public boolean getAndSet(boolean v){
        boolean v1=value;
        value=v;
        return v1;
    }


    public void set(boolean b) {
        value=b;
    }

    public boolean get() {
        return value;
    }
}
