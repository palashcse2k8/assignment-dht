// Java implementation for a client 
// Save file as Client.java 
  
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner; 
  
// Client class 
public class Client extends Thread
{ 
    static String roll_number;
    static String blockNumber;
    
    
    public static String getMd5(String input) 
    { 
//        System.out.println(input);
    	try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
    
    @Override
    public void run()
    { 
//       System.out.println("creating client");
    	try
        { 
//            Scanner scn = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getLocalHost();
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 5056);
      
            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
            // the following loop performs the exchange of 
            // information between client and client handler 
        	

            while (true)  
            { 
            	roll_number = dis.readUTF();
            	
            	if (roll_number.equals("END")) {
//                  System.out.println("Closing this connection : " + s); 
                  s.close(); 
//                  System.out.println("Connection closed"); 
                  break; 	
            	}
            	
            	blockNumber = dis.readUTF();
//                System.out.println("Block Number : " + blockNumber + ", roll_number:  " + roll_number);
//                String tosend = scn.nextLine();
                
                int range = 1000000;
                int nonce;
                
                String S = "00" + roll_number.substring(roll_number.length() - 3);
                for (int i = 1; i <= range; i++) {
                	nonce = Integer.parseInt(blockNumber)*range + i;
                	String result = getMd5(roll_number + nonce );
//                	System.out.println(result + " " + S);
                	
                	int match = 0;
                	for (int j = 0; j < S.length(); j++) {
						if(S.charAt(j) == result.charAt(j)) {
							match++;
						}
					}
                	
                	if (match == 5) {
                		dos.writeUTF(result);
                	} else {
//                		System.out.println("not matched!");
                	}
                	
				}
                
                dos.writeUTF("END");
              }
              
            // closing resources 
//            scn.close();  
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 
    } 
} 