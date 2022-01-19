package meneyCatch;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class TestBase64 {
	
	/***编码**/
	public static String encoder(byte[] bytes) throws Exception {
		Encoder encoder = Base64.getUrlEncoder();
		bytes = encoder.encode(bytes);
		return new String(bytes,"UTF8");
	}
	
	/***解码*/
	public static  byte[] decode(byte[] bytes) throws Exception {
		Decoder decoder= Base64.getUrlDecoder();
		return decoder.decode(bytes);
	}

	
	private Cipher encryptCipher; // 负责加密工作
    private Cipher decryptCipher; // 负责解密工作
    
    public TestBase64(String rules) throws Exception {
        // 1.实例化AES算法密钥生成器
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // 2.根据传入的字节数组,生成一个128位的随机源
        //keygen.init(128, new SecureRandom(rules.getBytes()));
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(rules.getBytes());
        // 3.生成密钥
        SecretKey secretKey = keygen.generateKey();
        // 4.生成Cipher对象,指定其支持AES算法
        encryptCipher = Cipher.getInstance("AES");
        decryptCipher = Cipher.getInstance("AES");
        // 5.初始化加密对象及解密对象
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
    }
 
    public byte[] encryptAES(byte[] source) throws Exception {
        return encryptCipher.doFinal(source);
    }
 
    public byte[] decryptAES(byte[] source) throws Exception {
        return decryptCipher.doFinal(source);
    }

	
	public static void main(String[] args) throws Exception {
		TestBase64 testBase64= new TestBase64("hdlh@123");

		byte[] bs = "{\"dateTime\":1632280432956,\"opration\":\"1\",\"role\":\"测试\",\"userName\":\"test3\"}".getBytes("UTF8");
		byte[] bsAes = testBase64.encryptAES(bs);
		
		//加密
		String bsstr = TestBase64.encoder(bsAes); //TestBase64.encoder(bsAes);
		
		//解密
		byte[] bsAes111 = TestBase64.decode(bsstr.getBytes("UTF8"));
		//String bbb = TestBase64.decode(bsstr.getBytes("UTF8"));
		//byte[] bsAes111 = bbb.getBytes("UTF8");
		byte[] dbsAes = testBase64.decryptAES(bsAes111);
		
		System.out.println(bsstr);
		System.out.println(new String(dbsAes));
		
	}
	
}
