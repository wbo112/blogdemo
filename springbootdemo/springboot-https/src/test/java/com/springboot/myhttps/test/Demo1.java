package com.springboot.myhttps.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

public class Demo1 {


    public static void main(String[] args) throws Exception {
        keyStore();
    }

    //打印当前系统所配置的全部安全提供者
    private static void printProviders() {
        // 遍历目前环境中的安全提供者
        for (Provider p : Security.getProviders()) {
            // 打印当前提供者信息
            System.out.println(p);
            // 遍历提供者Set实体
            for (Map.Entry<Object, Object> entry : p.entrySet()) {
                // 打印提供者键值
                System.out.println("\t" + entry.getKey());
            }
        }


    }


    private static void keyPairGen() throws NoSuchAlgorithmException {
        // 实例化KeyPairGenerator对象
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
// 初始化KeyPairGenerator对象
        kpg.initialize(1024);
// 生成KeyPair对象
        KeyPair keys = kpg.genKeyPair();
        System.out.println(keys.getPrivate().getAlgorithm());
        System.out.println(keys.getPrivate().getEncoded());
        System.out.println(keys.getPrivate().getFormat());
        System.out.println("===");
        System.out.println(keys.getPublic());
    }

    private static void keyPairGen1() throws Exception {
        // 实例化KeyPairGenerator对象，并指定RSA算法
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
// 初始化KeyPairGenerator对象
        keyPairGen.initialize(1024);
// 生成KeyPair对象
        KeyPair keyPair = keyPairGen.generateKeyPair();

// 获得私钥密钥字节数组。实际使用过程中该密钥以此种形式保存传递给另一方
        byte[] keyBytes = keyPair.getPrivate().getEncoded();
// 由私钥密钥字节数组获得密钥规范
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
// 实例化密钥工厂，并指定RSA算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
// 生成私钥
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
    }

    private static void keyStore() throws Exception {
        // 加载密钥库文件
        FileInputStream is = new FileInputStream("D:\\code\\gitcode\\blogdemo\\springbootdemo\\springboot-https\\src\\main\\resources\\certs\\server_abc.p12");
// 实例化KeyStore对象
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
// 加载密钥库，使用密码"password"

        ks.load(is, "123456".toCharArray());

        // 获得别名为"alias"所对应的私钥
        PrivateKey key = (PrivateKey) ks.getKey("abc", "123456".toCharArray());
        Enumeration<String> aliases = ks.aliases();
        while(aliases.hasMoreElements()){
           // System.out.println(aliases.nextElement());
        boolean certificateEntry = ks.isCertificateEntry(aliases.nextElement());
        System.out.println(certificateEntry);
        }
        // 获得私钥项
        KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry("abc", new KeyStore.PasswordProtection("123456".toCharArray()));
// 获得私钥
        PrivateKey privateKey = pkEntry.getPrivateKey();
        Certificate[] certificateChain = pkEntry.getCertificateChain();
        Arrays.stream(certificateChain).forEach(System.out::println);
// 关闭文件流
        is.close();
    }
}
