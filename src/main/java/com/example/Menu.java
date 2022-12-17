package com.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    private static Scanner keyboardScanner = new Scanner(System.in);
    private static ArrayList<String> menuOptions;

    private static final String ENCRYPTED_FILE_NAME = "ciphertext.txt";

    public Menu(){
        menuOptions = new ArrayList<>();
        menuOptions.add("1. Encrypt file");
        menuOptions.add("2. Decrypt file");
        menuOptions.add("3. Exit");
    }

    public void run(){
        System.out.println("Welcome");
        boolean run = true;
        while(run){
            try{
                System.out.println("\nSelect option:");

                for(int i = 0; i < menuOptions.size(); i++){
                    System.out.println(menuOptions.get(i));
                }

                int selection = keyboardScanner.nextInt() - 1;
                keyboardScanner.nextLine();
                
                System.out.println("Selection [" + menuOptions.get(selection) + "]");

                switch(selection){
                    case 0 -> {
                        encryptFile();
                    }
                    case 1 -> {
                        decryptFile();
                    }
                    case 2 -> {
                        run = false;
                    }
                }
            }
            catch(InputMismatchException e){
                System.out.println("Error: input was not an integer.");
                keyboardScanner.nextLine();
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("Error: menu option does not exist.");
            }
        }
        System.out.println("Goodbye");
    }

    private void encryptFile(){
        System.out.println("Please enter file name:");
        String fileName = keyboardScanner.nextLine();
        String plainText = FileIO.readFile(fileName);
        if(plainText != null){
            String key = Key.generateHash();
            String encypted = AesCipher.encryptString(plainText, key);
            FileIO.writeFile(encypted, true);
            System.out.println("Key: " + key);
            System.out.println("File encrypted");
        }
    }

    private void decryptFile(){
        System.out.println("Please enter the key:");
        String key = keyboardScanner.nextLine();
        String encryptedText = FileIO.readFile(ENCRYPTED_FILE_NAME);
        String decrypted = AesCipher.decryptString(encryptedText, key);
        if(decrypted != null){
            FileIO.writeFile(decrypted, false);
            System.out.println("File decrypted");
        }
    }
}
