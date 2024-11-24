package com.springboot.myhttps.test;

import static org.junit.Assert.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import org.junit.Before;
import org.junit.Test;
/**
 * SSLSocketServer测试
 *
 * @author梁栋
 *
 */
public class SSLSocketServerTest {
      // 加密端口
      private int port;
      @Before
      public void init() {
            port = 8080;
            System.setProperty("javax.net.ssl.keyStore", "abc.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            System.setProperty("javax.net.ssl.trustStore", "abc2.keystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");
      }
      @Test
      public void testServer() throws Exception {
            SSLServerSocket serverSocket = null;
            try {
                  // 初始化SSLServerSocketFactory
                  SSLServerSocketFactory socketFactory = (SSLServerSocketFactory)
                  SSLServerSocketFactory.getDefault();
                  // 构建ServerSocket实例
                  serverSocket = (SSLServerSocket) socketFactory.
                  createServerSocket(port);
            } catch (Exception e) {
                  e.printStackTrace();
                  fail();
            }
            while (true) {
                  try {
                      // SSLServerSocket阻塞，等待客户端连接请求
                      SSLSocket socket = (SSLSocket) serverSocket.accept();
                      // 获得输入流
                      ObjectInputStream input = new
ObjectInputStream(socket.getInputStream());
                      // 获得用户名密码
                      @SuppressWarnings("unchecked")
                      Map<String, String> map = (Map<String, String>) input.readObject();
                      String username = map.get("USERNAME");
                      String password = map.get("PASSWORD");
                      // 验证用户名密码
                      assertNotNull(username);
                      assertEquals("123456", password);
                      // 获得输出流
                      ObjectOutputStream output = new
                      ObjectOutputStream(socket.getOutputStream());
                      // 输出OK
                      output.writeUTF("OK");
                      output.flush();
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
}