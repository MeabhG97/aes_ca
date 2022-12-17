package com.example;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Key {
    private static final String KEY_TYPE = "AES";
    private static final int KEY_SIZE = 256;

    private static final String BAD_ALGORITHM = "Bad algorithm";

    public static String generateHash(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_TYPE);
            keyGenerator.init(KEY_SIZE);
            SecretKey key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        }
        catch(NoSuchAlgorithmException e){
            throw new KeyException(BAD_ALGORITHM);
        }
    }
}
