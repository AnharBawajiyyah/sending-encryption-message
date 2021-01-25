/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security_project;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author rakhaa.
 */
public class Bob {
    byte[] cipherSymmetric; 
    byte[] cipherMsg ; 
    byte[] symmetricKey ;
    private PrivateKey bobPriKey; 

  

    public Bob() {
 
    }
    
    public void decryptSymmetric(byte[] encryptedBytesOfSym , Cipher rsa) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{

        rsa.init(Cipher.DECRYPT_MODE , bobPriKey); // initalize decrypt mode 
        byte[] decryptSymmetric = rsa.doFinal(encryptedBytesOfSym); // convert to array of bytes
        System.out.println("Symmertic after decrypt\t"+new String(decryptSymmetric) + "\n");
        this.symmetricKey = decryptSymmetric; // set the symmetric after decrypt 

    }
    public void decryptMsg(byte[] encryptedBytesOfMsg , Cipher aes , IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
        
        SecretKey byteToSymetric = new SecretKeySpec(symmetricKey , 0 , symmetricKey.length , "AES"); // convert bytes to key to decrypt
        aes.init(Cipher.DECRYPT_MODE , byteToSymetric , iv); //decrypt msg using symmetric key and CBC mode 
        byte[] decryptMSG = aes.doFinal(encryptedBytesOfMsg); // decrypting the msg 
        String ec = new String(decryptMSG);
        System.out.println("MSG after decrypt\t" + ec + "\n");


    }
    public PublicKey getPublicKey() throws NoSuchAlgorithmException{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA"); // generate RSA keys
        KeyPair myPair = generator.generateKeyPair();
        PublicKey publickey = myPair.getPublic(); 
        PrivateKey privateKey = myPair.getPrivate();

        this.bobPriKey = privateKey ; // set private key to use it when decrypt the Symmetric 
        return publickey ;
    }
    
    
    
}
