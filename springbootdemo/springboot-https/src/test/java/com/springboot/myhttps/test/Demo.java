package com.springboot.myhttps.test;

import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;


public class Demo {
    @Test
    public void a() throws IOException, CertificateEncodingException {
        // 初始化SSLSocketFactory
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
// 构建SSLSocket
        SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 8080);
        // 握手，开始会话
        socket.startHandshake();
// 获得Session
        SSLSession session = socket.getSession();
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
// 结束Socket
        socket.close();
    }
}
