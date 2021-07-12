/*
 * Decompiled with CFR 0.137.
 * 
 * Could not load the following classes:
 *  sun.misc.BASE64Decoder
 *  sun.misc.BASE64Encoder
 */
package cmecf.programs;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encrypter {
    static String passPhrase = "ZmIL^k4NwZG83U2672faw#l6LoM$gP7mH";
    Cipher enCipher;
    Cipher deCipher;

    public Encrypter() {
        byte[] salt = new byte[]{-87, -101, -56, 50, 86, 52, -29, 3};
        int iterationCount = 19;
        try {
            PBEKeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            this.enCipher = Cipher.getInstance(key.getAlgorithm());
            this.deCipher = Cipher.getInstance(key.getAlgorithm());
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            this.enCipher.init(1, (Key)key, paramSpec);
            this.deCipher.init(2, (Key)key, paramSpec);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = this.enCipher.doFinal(utf8);
            return new BASE64Encoder().encode(enc);
        }
        catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String str) {
        try {
            byte[] dec = new BASE64Decoder().decodeBuffer(str);
            byte[] utf8 = this.deCipher.doFinal(dec);
            return new String(utf8, "UTF8");
        }
        catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        if (args == null || args[0] == null) {
            System.out.println("please provide a string to encrypt.");
        }
        String str = args[0];
        try {
            Encrypter enc = new Encrypter();
            String es = enc.encrypt(str);
            System.out.println("encrypted string: " + es);
            System.out.println("decrypted string: " + enc.decrypt(es));
            System.out.println("orig decrypted string: " + enc.decrypt(str));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

