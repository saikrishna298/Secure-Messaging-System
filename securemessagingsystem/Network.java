package securemessagingsystem;

/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji
 * Network.java
 **********************************/

import java.math.BigInteger;

public class Network {
    
    private Packet inFromSender;
    private Packet outToReceiver;
    private long errorNum = 0;
    
    public Network(){  }
    
    public void receiveFromSender( Packet in){
        inFromSender = in;
    }
    
    public Packet sendToReceiver(){
        BigInteger message = new BigInteger
        (inFromSender.getMessage().add(BigInteger.valueOf(errorNum)).toString());
        
        BigInteger digitalSignature = new BigInteger
        (inFromSender.getDigitalSignature().toString());
        
        BigInteger ks = new BigInteger
        (inFromSender.getKs().toString());

        outToReceiver = new Packet(digitalSignature,ks,message);
        return outToReceiver;
    }
    
    public void setInternetConnection(int errorInt){
        errorNum = (long)errorInt;
    }
}

