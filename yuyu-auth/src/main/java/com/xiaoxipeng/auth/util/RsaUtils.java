package com.xiaoxipeng.auth.util;

import com.xiaoxipeng.common.exception.YuyuException;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class RsaUtils {

    private static PrivateKey loadPrivateKey(String path, String algorithm) throws Exception {
        try (InputStream is = RsaUtils.class.getClassLoader().getResourceAsStream(path)) {
            assert is != null;
            String privateKeyPEM = new String(is.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return keyFactory.generatePrivate(keySpec);
        }
    }

    private static PublicKey loadPublicKey(String path, String algorithm) throws Exception {
        try (InputStream is = RsaUtils.class.getClassLoader().getResourceAsStream(path)) {
            assert is != null;
            String publicKeyPEM = new String(is.readAllBytes())
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return keyFactory.generatePublic(keySpec);
        }
    }

    public static KeyPair loadKeyPair(String privateKeyPath, String publicKeyPath, String algorithm)  {

        // 读取私钥
        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        try {
            log.info("----------- Load  private  key   -----------");
            privateKey = loadPrivateKey(privateKeyPath, algorithm);

            log.info("----------- Load  public  key    -----------");
            // 读取公钥
            publicKey = loadPublicKey(publicKeyPath, algorithm);
        } catch (Exception e) {
            throw new YuyuException("Load RSA key failed", e);
        }

        // 创建KeyPair对象
        KeyPair keyPair = new KeyPair(publicKey, privateKey);
        log.info("----------- Load   RSA  key  success -----------");
        return keyPair;
    }

}
