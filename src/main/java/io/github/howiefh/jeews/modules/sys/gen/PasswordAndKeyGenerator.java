package io.github.howiefh.jeews.modules.sys.gen;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;

public class PasswordAndKeyGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        DefaultHashService hashService = new DefaultHashService(); // 默认算法SHA-512

        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String salt = randomNumberGenerator.nextBytes().toHex();

        HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes("123456")).setSalt(ByteSource.Util.bytes(salt)).setIterations(2)
                .build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(salt);
        System.out.println(hex);
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey deskey = keygen.generateKey();
        System.out.println(Base64.encodeToString(deskey.getEncoded()));
    }
}