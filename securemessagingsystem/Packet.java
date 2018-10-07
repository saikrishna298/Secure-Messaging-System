package securemessagingsystem;
/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji 
 * Packet.java
 **********************************/

import java.math.BigInteger;

public class Packet {
    private BigInteger digitalSigniture = new BigInteger("0");
    private BigInteger ks = new BigInteger("0");
    private BigInteger message = new BigInteger("0");
    
    public Packet(BigInteger ds, BigInteger k, BigInteger m){
        digitalSigniture = ds;
        ks = k;
        message = m;
    }
    
    public void setDigitalSignature(BigInteger d){
        digitalSigniture = d;
    }
    
    public BigInteger getDigitalSignature(){
        return digitalSigniture;
    }
    
    public void setKs(BigInteger k){
        ks = k;
    }
    
    public BigInteger getKs(){
        return ks;
    }
    
    public void setMessage(BigInteger m){
        System.out.println("testing");
        message = m;
    }
    
    public BigInteger getMessage(){
        return message;
    }
}
