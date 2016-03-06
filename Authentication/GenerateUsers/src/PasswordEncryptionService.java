/*
* Adapted from the 
* Source code from 
* https://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
*
*/
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncryptionService {

    
//default constructor
public PasswordEncryptionService ()
{

}
 public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
   throws NoSuchAlgorithmException, InvalidKeySpecException {
  // Encrypt the clear-text password using the same salt that was used to
  // encrypt the original password
  byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

  // Authentication succeeds if encrypted password that the user entered
  // is equal to the stored hash
  return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
 }

 public byte[] getEncryptedPassword(String password, byte[] salt)
   throws NoSuchAlgorithmException, InvalidKeySpecException {
  // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
  // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
  String algorithm = "PBKDF2WithHmacSHA1";
  // SHA-1 generates 160 bit hashes, so that's what makes sense here
  int derivedKeyLength = 160;
  // Pick an iteration count that works for you. The NIST recommends at
  // least 1,000 iterations:
  // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
  // iOS 4.x reportedly uses 10,000:
  // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
  int iterations = 20000;

  KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

  SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

  return f.generateSecret(spec).getEncoded();
 }

 public byte[] generateSalt() throws NoSuchAlgorithmException {
  // VERY important to use SecureRandom instead of just Random
  SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

  // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
  byte[] salt = new byte[8];
  random.nextBytes(salt);

  return salt;
 }
 
 //generate the encrypted password and salt based on the given clear text pwd
 public void generateEncryptedPwd(String pwd) throws NoSuchAlgorithmException
 {
     byte[] salt = new byte[8];
     byte[] encryptedPwd = null;
     String enPwd = null;
     try {
         salt = generateSalt();
     } catch (NoSuchAlgorithmException ex) {
         Logger.getLogger(PasswordEncryptionService.class.getName()).log(Level.SEVERE, null, ex);
     }
     
     try {
         encryptedPwd = getEncryptedPassword(pwd, salt);
     } catch (NoSuchAlgorithmException ex) {
         Logger.getLogger(PasswordEncryptionService.class.getName()).log(Level.SEVERE, null, ex);
     } catch (InvalidKeySpecException ex) {
         Logger.getLogger(PasswordEncryptionService.class.getName()).log(Level.SEVERE, null, ex);
     }

  
    try {
        enPwd = new String(encryptedPwd,"UTF-8");
    } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(PasswordEncryptionService.class.getName()).log(Level.SEVERE, null, ex);
    }
     
     System.out.println("Pwd:" + pwd + ",encryptedPwd: " + encryptedPwd
            + "salt:" + salt);
     System.out.println("Pwd:" + pwd + ",encryptedPwd: " + enPwd
            + "salt:" + salt);
     
    try {
        if (authenticate(pwd, encryptedPwd, salt))
        {
            System.out.println ("Success logon");
        }
        else
        {
            System.out.println ("Failed logon");
        }
    } catch (InvalidKeySpecException ex) {
        Logger.getLogger(PasswordEncryptionService.class.getName()).log(Level.SEVERE, null, ex);
    }
 }
}
