package com.qipai.gansuactivity;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtils {
    public static String RSAjiami(String s,String modulus,String exports) {
        String s1="";
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//                    String PrivateExponent = privateKey.getPrivateExponent().toString(16);//公钥
//                    KLog.e("TAs", PrivateExponent);//
            //s1 = encryptByPublicKey(s.getBytes(), PrivateExponent);
            BigInteger b1 = publicKey.getModulus();
            BigInteger b2 = publicKey.getPublicExponent();
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(modulus, 16), new BigInteger(exports, 16));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) factory.generatePublic(keySpec);
//                    PublicKey a = (RSAPublicKey) factory.generatePublic(keySpec);
//                    KLog.e("TAG", a.getClass().getName());
            // 对数据加密

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(s.getBytes());

            String encryptedString = Base64.byte2HexStr(encryptedData);//加密后的密码 密文
            StringBuffer sb = new StringBuffer(encryptedString);
            s1 = sb.toString();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return s1;
    }
}
