package securemessagingsystem;
/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji 
 * Sender.java
 **********************************/
import java.math.BigInteger;

public class Sender {
    
    private RSAAlgorithm rsa;
    private String message;
    public Receiver receiver;
    public int[] inputVals = new int[300];
    
    public Sender(Receiver rec){
        message = "default";
        rsa = new RSAAlgorithm();
        rsa.generateKeys();
        receiver = rec;
        System.out.println("sender constructor");
        int asciiValue = 32;
        for(int i = 100; i < 300 && asciiValue < 126; i++){
            inputVals[asciiValue] = i;
            asciiValue++;
        }
    }
    
    public Sender(Receiver rec,String input){
        message = input;
        rsa = new RSAAlgorithm();
        rsa.generateKeys();
        receiver = rec;
        
        int asciiValue = 33;
        for(int i = 100; i < 300 && asciiValue < 126; i++){
            inputVals[asciiValue] = i;
            asciiValue++;
        }
    }
    
    
   
    public Packet encrypt(){
        if(message == null){
            BigInteger zero = new BigInteger("0");
            return new Packet(zero,zero,zero);
        }
        //1 get message - This step is used to convert text into numerical
            //representation
        generateMessage();
        
        //2 hash m to produce message digest H(m)
        BigInteger hash = new BigInteger(hashMessage().toString());
       
        //3 encString H(m) with sender private key
        //I think decryption encrypts with the private key
        BigInteger digitalSignature = new BigInteger
        (rsa.decryption(hash.toString()).toString());

        //4 Integrate with digital signature
          //put into packet obj in step 8
        
        //5 Generate symmetric key
        SymmetricEncryption se = new SymmetricEncryption();
        se.generateKey();
        BigInteger sKey = new BigInteger(se.getKey().toString());
        
        //setting up receiver's public key
        RSAAlgorithm recRSA = new RSAAlgorithm();
        recRSA.generateKeys();
        recRSA.setN(receiver.getN());
        recRSA.setE(receiver.getE());
        
        //6 Encrypt key with Receiver's public key
        BigInteger encSKey = new BigInteger
        (recRSA.encryption(sKey.toString()).toString());
        
        //7 Encrypt message from 4 with key
        BigInteger encMessage = new BigInteger
        (se.encrypt(message).toString());
        BigInteger encDS = new BigInteger
        (se.encrypt(digitalSignature.toString()).toString());
        
       
        //8 Integrate with encrypted message
        Packet finalMessage = new Packet
        (encDS,encSKey,encMessage);
        return finalMessage;
    }
    
    //transforms message into a string of 3 digit, zero padded blocks
    public void generateMessage(){//
        String result = "";
        for(int i = 0; i < message.length(); i++){
            Integer charToInt = (int)message.charAt(i);
            result += inputVals[charToInt];
        }
        message = result;
    }
    
    public BigInteger hashMessage(){
        BigInteger total = new BigInteger("0"); 
         BigInteger temp = new BigInteger("0");
        long prime = 41;// could be bigger,  this is arbitrary
        for(int i = 0; i < message.length(); i++){
            String s = message.substring(i,i+1);
            temp = new BigInteger(s);
            total = new BigInteger(total.add(temp).toString());
        }
        return total.mod(BigInteger.valueOf(prime));
    }
    
    public BigInteger getE(){
        return rsa.getE();
    }
    
    public BigInteger getN(){
        return rsa.getN();
    }  
    
    public void setMessage(String m){
        message = m;
    }
}