package com.springboot.myhttps.test;

import static org.junit.Assert.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.junit.Before;
import org.junit.Test;
/**
 * SSLSocket测试
 *
 * @author梁栋
 *
 */
public class SSLSocketClientTest {
      private int port;
      private String hostname;
      @Before
      public void init() {
            hostname = "localhost";
            port = 8080;
            System.setProperty("javax.net.ssl.keyStore", "abc.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            System.setProperty("javax.net.ssl.trustStore", "abc2.keystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");
      }
      @Test
      public void testClient() {
            try {
                    // 构建SSLSocketFactory
                    SSLSocketFactory socketFactory = (SSLSocketFactory)
                    SSLSocketFactory.getDefault();
                    // 初始化SSLSocket实例
                    SSLSocket socket = (SSLSocket)
                      socketFactory.createSocket(hostname,port);
                    // 获得输出流
                    ObjectOutputStream output = new
                    ObjectOutputStream(socket.getOutputStream());
                    // 构建一个Map对象，用于记录用户名密码
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("USERNAME", "Snowolf");
                    map.put("PASSWORD", "123456");
                    // 输出
                    output.writeObject(map);
                    output.flush();
                    // 获得输入流
                    ObjectInputStream input = new
                    ObjectInputStream(socket.getInputStream());
                    // 验证输入
                    assertEquals("OK", input.readUTF());
                    // 关闭流、套接字
                    output.close();
                    input.close();
                    socket.close();
            } catch (IOException e) {
                    e.printStackTrace();
                    fail();
            }
      }
}