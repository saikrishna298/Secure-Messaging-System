package securemessagingsystem;
/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji 
 * SecureMessagingSystem.java
 **********************************/

import java.math.BigInteger;
import java.util.Scanner;
public class securemessagingsystem {

    public static void main(String[] args) {
    
        
        boolean again  = true;
        char response;
        int errorNum;
        String message;
        
        Receiver r = new Receiver();
        Network network = new Network();
        
        
        while(again){
            Scanner scan = new Scanner(System.in);
            System.out.println("Simulate a network error? (Y/N)");
            response = scan.nextLine().charAt(0);
            
            if(response != 'N' && response != 'n'){
                System.out.println("Enter a value for the network error");
                errorNum = scan.nextInt();
                scan = new Scanner(System.in);
                network.setInternetConnection(errorNum);
            }
            
            System.out.println("Enter in data to encrypt");
            message = scan.nextLine();
            
            Sender s = new Sender(r,message);
            network.receiveFromSender(s.encrypt());
            r.receiveFromNetwork(network);
            
            System.out.println(r.decryption(s.getN(),s.getE()));
            
            System.out.println("Send more data? (Y/N)"); 
            response = scan.nextLine().charAt(0);
        
            if(response == 'N' || response == 'n')
                again = false;
        }
    }
}
