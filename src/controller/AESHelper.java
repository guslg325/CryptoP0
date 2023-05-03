package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author gustavo
 */
public class AESHelper {
    private String directory;
    private SecretKey key;
    private IvParameterSpec initVector;
    
    public AESHelper(String directory, String key){
        try{
            this.directory = directory;
            this.key = getKeyFromPassword(key,"12345");
            this.initVector = generateIv();
        }catch(Exception e){
            System.out.println("Error al crear objeto helper.");
        }
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }
    
    public boolean encrypt(){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, initVector);
            FileInputStream inputStream = new FileInputStream(new File(directory));
            FileOutputStream outputStream = new FileOutputStream("texto_c.txt");
            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
            inputStream.close();
            outputStream.close();
            
            return true;
        }catch(Exception e){
            System.out.println("Error al cifrar: "+e.toString());
            return false;
        }
    }
    
    public boolean decrypt(){
        //TODO decrypt
        try{
            FileInputStream inputStream = new FileInputStream(new File(directory));
            byte[] fileIv = new byte[16];
            inputStream.read(fileIv);
        
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key,  new IvParameterSpec(fileIv));
            FileOutputStream outputStream = new FileOutputStream("texto_d.txt");
            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
            
            inputStream.close();
            outputStream.close();
            
            return true;
        }catch(Exception e){
            System.out.println("Error al cifrar: "+e.toString());
            return false;
        }
    }
    
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
            .getEncoded(), "AES");
        return secret;
    }
    
    public static IvParameterSpec generateIv() {//Generar un vector de inicializacion
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
