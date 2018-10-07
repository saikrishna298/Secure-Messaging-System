package securemessagingsystem;
/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji	
 * SymmetricEncryption.java
 **********************************/

import java.math.BigInteger;
import java.util.Random;

public class SymmetricEncryption {
    private BigInteger key;
    
    public SymmetricEncryption(){   
    }
    
    public void generateKey(){
        Random rng = new Random();
        Integer random = rng.nextInt(9);//arbitrary, could be changed
        BigInteger result = new BigInteger(random.toString());
        key = result;
    }
    
    public void setKey(BigInteger inKey){
        key = inKey;
    }
    
    public BigInteger encrypt(String input){
       
        String encString = "";
        Integer charToInt = new Integer(0);
        for(int i = 0; i < input.length(); i++){
            charToInt = Integer.parseInt(input.substring(i, i+1));
            charToInt += Integer.parseInt(key.toString());
            charToInt %= 10;
            encString += charToInt;
        }
        BigInteger result = new BigInteger(encString);
   
        return result;
    }
    
    public BigInteger decrypt(String input){
        String encString = "";
        Integer charToInt = new Integer(0);
        for(int i = 0; i < input.length(); i++){
            charToInt = Integer.parseInt(input.substring(i, i+1));
            charToInt -= Integer.parseInt(key.toString());
            
            if(charToInt < 0)
                charToInt += 10;
            encString += charToInt;
        }
        BigInteger result = new BigInteger(encString);
     
        return result;
    }
    
    public BigInteger getKey(){
        return key;
    }
}
