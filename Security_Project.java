/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Names :Lama mohsen - Anhar Omar - Rakhaa ismail
// Section : GAR

package security_project;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author rakhaa - lama - anhar.
 */
public class Security_Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
        // TODO code application logic here

        System.out.println("Enter aliace message : \n");
        String msg;
        Scanner s = new Scanner(System.in);
        msg = s.nextLine();

        try {
            Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding"); // AES cipher object        // AES -> ENCRYPT MSG     
            KeyGenerator kg = KeyGenerator.getInstance("AES");// AES key 

            Cipher rsa = Cipher.getInstance("RSA"); //  RSA object to encrypt Symmetric key before send it to bob  

            Bob bob = new Bob(); // create objects from bob to get the public key also to send the cipher symmetric and encrypt msg
            PublicKey publickey = bob.getPublicKey(); // get the public key from bob
        

            SecretKey symmetricKey = kg.generateKey(); // generate AES key 
            byte[] symmetricKey_Byte = symmetricKey.getEncoded(); // convert to array of bytes
            System.out.println("AES Key BEFORE encrypt\t" + new String(symmetricKey_Byte) +"\n");

            rsa.init(Cipher.ENCRYPT_MODE, publickey); // initalize enctypte mode
            byte[] encryptedBytesOfSym = rsa.doFinal(symmetricKey_Byte); //  encrypt symmetric key using bob public key 
            System.out.println("AES Key AFTER encryp\t"+ new String(encryptedBytesOfSym)+"\n");

            String iv_1 = "Erakhaaanharlama";  
            IvParameterSpec iv = new IvParameterSpec(iv_1.getBytes("UTF-8"));  

            aes.init(Cipher.ENCRYPT_MODE, symmetricKey, iv); // initalize enctypte mode
            byte[] enc = msg.getBytes(); // convert to array of bytes
            byte[] encryptedBytesOfMsg = aes.doFinal(enc); // encrypt msg using symmetric key and CBC mode 
            System.out.println("Message AFTER encrypt\t" + new String(encryptedBytesOfMsg)+"\n");

            bob.decryptSymmetric(encryptedBytesOfSym, rsa); // call method to decrypt symmetric key 
            bob.decryptMsg(encryptedBytesOfMsg, aes, iv); // call method to decrypt the msg

        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

}
