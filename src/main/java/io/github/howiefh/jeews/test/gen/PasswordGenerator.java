package io.github.howiefh.jeews.test.gen;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;

public class PasswordGenerator {
	public static void main(String[] args) {
		DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512  
        
		SecureRandomNumberGenerator randomNumberGenerator =  new SecureRandomNumberGenerator();  
		randomNumberGenerator.setSeed("123".getBytes());  
		String salt = randomNumberGenerator.nextBytes().toHex();
        
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("123456"))  
		            .setSalt(ByteSource.Util.bytes(salt)).setIterations(2).build();  
		String hex = hashService.computeHash(request).toHex();
        System.out.println(salt);
        System.out.println(hex);
	}
}