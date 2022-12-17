package com.example;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

public class AesCipher {
    private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";
    private static final String KEY_TYPE = "AES";
    private static final int BITS_PER_BYTE = 8;
    private static final int KEY_BITS_SMALL = 128;
    private static final int KEY_BITS_MEDIUM = 192;
    private static final int KEY_BITS_LARGE = 256;

    private static final String INVALID_KEY_SIZE = "Key must be 128, 192 or 256 bits long";

    public static Boolean isValidKeySize(byte[] key){
        int keyLengthBits = key.length * BITS_PER_BYTE;

        return keyLengthBits == KEY_BITS_SMALL
            || keyLengthBits == KEY_BITS_MEDIUM 
            || keyLengthBits == KEY_BITS_LARGE;
    }

    public static String encryptString(String plainText, String key){
        byte[] keyBytes = Base64.getDecoder().decode(key);

        if(!AesCipher.isValidKeySize(keyBytes)){
            throw new AesCipherException(INVALID_KEY_SIZE);
        }

        try{
            SecretKey secretKey = new SecretKeySpec(keyBytes, KEY_TYPE);
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            
            return Base64.getEncoder().encodeToString(ivBytes) + "|" 
                + Base64.getEncoder().encodeToString(cipherText);
        }
        catch(NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException 
            |IllegalBlockSizeException |BadPaddingException |InvalidParameterSpecException e){
                throw new AesCipherException(e.getMessage());
        }
    }

    public static String decryptString(String cipherText, String key){
        String plainText = null;

        byte[] keyBytes = Base64.getDecoder().decode(key);

        if(!AesCipher.isValidKeySize(keyBytes)){
            System.out.println("Error: invalid key");
        }
        else{
            try{
                String[] cipherTextParts = cipherText.split("\\|");
                byte[] ivBytes = Base64.getDecoder().decode(cipherTextParts[0]);
                byte[] cipherTextBytes = Base64.getDecoder().decode(cipherTextParts[1]);
    
                SecretKey secretKey = new SecretKeySpec(keyBytes, KEY_TYPE);
                Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
    
                plainText = new String(cipher.doFinal(cipherTextBytes));
            }
            catch (NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException
                |IllegalBlockSizeException |BadPaddingException |InvalidAlgorithmParameterException e){
                    throw new AesCipherException(e.getMessage());
            }
        }

        return plainText;
    }
}
