package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIO {

    private static final String ENCRYPTED_FILE = "ciphertext.txt";
    private static final String DECRYPTED_FILE = "plaintext.txt";

    private static final String FILE_READ_ERROR = "Error: reading from file";
    private static final String FILE_WRITE_ERROR = "Error: writing to file";
    
    public static String readFile(String fileName){
        String inputText = null;
        try{
            Path readPath = Paths.get(fileName);
            inputText = Files.readAllLines(readPath).get(0);
        }
        catch(IOException e){
            System.out.println(FILE_READ_ERROR);
        }
        return inputText;
    }

    public static void writeFile(String text, boolean encrypted){
        try{
            Path writePath;
            if(encrypted){
                writePath = Paths.get(ENCRYPTED_FILE);
            }
            else{
                writePath = Paths.get(DECRYPTED_FILE);
            }

            byte[] stringBytes = text.getBytes();
            Files.write(writePath, stringBytes);
        }
        catch(IOException e){
            System.out.println(FILE_WRITE_ERROR);
        }
    }
}
