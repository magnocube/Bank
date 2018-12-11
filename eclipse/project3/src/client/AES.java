package client;




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
    private static String key = "Bar12345Bar12345"; // 128 bit key
    private static byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01 };
    public static String encrypt(String dataIn) {
        dataIn=getDate()+dataIn;
        text = dataIn;

        try {
            // Create key and cipher

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

    public static String decrypt(String dataIn) {

        try {

            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(dataIn.getBytes());
            String decrypted = new String(cipher.doFinal(decodedBytes));
            String otherTime=decrypted.substring(0,13);
            long otherTime2=Long.parseLong(otherTime);
            System.out.println(otherTime2-getDate());
            if(Math.abs(otherTime2-getDate())<5000)
            {
                if(otherTime2>=lastTime)
                {
                    lastTime=otherTime2;
                    decrypted=decrypted.substring(13,decrypted.length());
                    return decrypted;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

   /* public static void main(String args[])
    {

        for (int i = 0; i < 10; i++) {
            String encrypted=encrypt("dsfasklfjadsklfjalk;dfjadskljfkadslf");
            System.out.println(encrypted);
            String decrypted=decrypt(encrypted);
            System.out.println(decrypted);
            System.out.println();
        }
    }*/
    private static long getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
       // System.out.println(date.getTime()); //2016/11/16 12:08:43
        return date.getTime();
    }

}