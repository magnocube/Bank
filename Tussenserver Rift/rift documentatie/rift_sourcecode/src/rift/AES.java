package rift;


import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static String text = "";
    static long lastTime=0;

    private static byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01 };
    public static String encrypt(String dataIn,String key) {
        text = dataIn;

        try {
            // Create key and cipher
            //about aes:google.nl
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes());
            encrypted = Base64.getEncoder().encode(encrypted);
            //String base64_encrypted = DatatypeConverter.printBase64Binary(encrypted);
            //System.err.println("  "+new String(encrypted));
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String dataIn,String key) {

        try {

            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(dataIn.getBytes());
            String decrypted = new String(cipher.doFinal(decodedBytes));

            return decrypted;


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }




}