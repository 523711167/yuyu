package com.xiaoxipeng.generatersa;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Rsa {

    @Test
    public void generateRsa() throws Exception {
        try {
            // 生成RSA密钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // 设置密钥长度
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // 获取公钥和私钥
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // 将密钥保存到本地文件
//            saveKeyToFile(publicKey, "public.key");
//            saveKeyToFile(privateKey, "private.key");

            // 保存为PEM格式（更常用的格式）
            saveKeyToPEMFile(publicKey, "public.pem", true);
            saveKeyToPEMFile(privateKey, "private.pem", false);

            System.out.println("密钥对已成功生成并保存到本地文件");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 保存密钥为二进制格式
    private static void saveKeyToFile(Object key, String fileName) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            if (key instanceof PrivateKey) {
                fos.write(((PrivateKey)key).getEncoded());
            } else if (key instanceof PublicKey) {
                fos.write(((PublicKey)key).getEncoded());
            }
        }
    }

    // 保存密钥为PEM格式
    private static void saveKeyToPEMFile(Object key, String fileName, boolean isPublic) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            String begin = isPublic ? "-----BEGIN PUBLIC KEY-----\n" : "-----BEGIN PRIVATE KEY-----\n";
            String end = isPublic ? "-----END PUBLIC KEY-----\n" : "-----END PRIVATE KEY-----\n";

            byte[] encoded;
            if (isPublic) {
                encoded = ((PublicKey)key).getEncoded();
            } else {
                encoded = ((PrivateKey)key).getEncoded();
            }

            String base64Encoded = Base64.getEncoder().encodeToString(encoded);

            // 每64个字符添加一个换行符
            StringBuilder pemContent = new StringBuilder();
            pemContent.append(begin);
            for (int i = 0; i < base64Encoded.length(); i += 64) {
                pemContent.append(base64Encoded.substring(i, Math.min(i + 64, base64Encoded.length())));
                pemContent.append("\n");
            }
            pemContent.append(end);

            fos.write(pemContent.toString().getBytes());
        }
    }

}
