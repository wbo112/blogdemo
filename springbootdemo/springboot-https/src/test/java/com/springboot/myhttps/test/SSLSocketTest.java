package com.springboot.myhttps.test;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.security.cert.Certificate;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.junit.Before;
import org.junit.Test;
/**
 * SSLSocket测试
 *
 * @author 梁栋
 * @version 1.0
 */
public class SSLSocketTest {
     private String hostname;
     private int port;
     @Before
     public void init() {
          hostname = "itunes.apple.com";
          port = 443;
    }
    @Test
    public void test() throws Exception {
        // debug模式
        // System.setProperty("javax.net.debug", "all");
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket(hostname, port);
        // 握手，开始会话
        socket.startHandshake();
        SSLSession session = socket.getSession();
        socket.close();
        // 获取服务器证书链
        Certificate[] servercerts = session.getPeerCertificates();
        for (Certificate cer : servercerts) {
           FileOutputStream f = new FileOutputStream
           (System.currentTimeMillis() + "-itunes.cer");
           DataOutputStream dos = new DataOutputStream(f);
           dos.write(cer.getEncoded());
           dos.flush();
           dos.close();
        }
    }
}