package securemessagingsystem;
/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji 
 * Receiver.java
 **********************************/

import java.math.BigInteger;

public class Receiver {
    
    private Packet receivedCipher;
    private RSAAlgorithm rsa;
    private String message = "";
    public int[] inputVals = new int[300];
    
    public Receiver(){
        rsa = new RSAAlgorithm();
        rsa.generateKeys();
        System.out.println("receiver constructor");
        int asciiValue = 33;
        for(int i = 100; i < 300 && asciiValue < 126; i++){
            inputVals[i] = asciiValue;
            asciiValue++;
        }
    }
    
    public String decryption(BigInteger n, BigInteger e){
       //1 receive data from sender
         //accomplished in receivedFromNetwork()
         
       //2 split session key and message
        //this is already done with the packet class
       
       //3 decrypt session key with receiver's private key
       BigInteger sKey = new BigInteger
        (rsa.decryption(receivedCipher.getKs().toString()).toString());
       //4 decrypt message with session key(symmetric key)
       SymmetricEncryption se = new SymmetricEncryption();
       se.setKey(sKey);
       message = se.decrypt(receivedCipher.getMessage().toString()).toString();
       
       BigInteger digitalSignature = new BigInteger
        (se.decrypt(receivedCipher.getDigitalSignature().toString()).toString());
       
       //5 split message and digital signature
       //already accomplished with packet class 
       
       //6 hash message
       BigInteger checkSum = new BigInteger(hashMessage
        (message).toString());
        
       //7 decrypt signature with sender public key
       RSAAlgorithm rsa = new RSAAlgorithm();
       rsa.setE(e);
       rsa.setN(n);
       BigInteger messageDigest = new BigInteger(rsa.encryption
        (digitalSignature.toString()).toString());
        
       //8 compare hashes from 6 and 7
       //9 if hashes are equal, message is ok
       generateMessage(message);
       
       if(messageDigest.equals(checkSum))
           return "Message has not been altered: " + message;
       else
           return "Message has been altered: " + message;
    }
    
    public void generateMessage(String m){
        String result = "";
        for(int i = 0; i < message.length(); i+=3){
            int intToChar = Integer.parseInt(m.substring(i, i+3));
            intToChar = (int)inputVals[intToChar];
            result += (char)intToChar;
        }
        message = result;
    }
    
    //This is supposed to return a string.  I have no idea why.  
      //This will instead instantiate the packet object receivedCipher
    public void receiveFromNetwork(Network net){
        receivedCipher = net.sendToReceiver();
    }
    
    public BigInteger getE(){
        return rsa.getE();
    }
    
    public BigInteger getN(){
        return rsa.getN();
    }
    
    public String getMessage(){
        return message;
    }
    
    public BigInteger hashMessage(String m){
        BigInteger total = new BigInteger("0");
         BigInteger temp = new BigInteger("0");
        long prime = 41;// could be bigger,  this is arbitrary
        for(int i = 0; i < m.length(); i++){
            String s = m.substring(i,i+1);
            temp = new BigInteger(s);
            total = new BigInteger(total.add(temp).toString());
        }
        return total.mod(BigInteger.valueOf(prime));
    }
}
