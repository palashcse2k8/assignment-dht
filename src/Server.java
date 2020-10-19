import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
public class Server 
{ 
    public static int clientCount = 0;
    public static int blockNumbers [] = new int [1024];
    public static String T = "1011312001";
    public int clientNumber;
    public static int findBlockNumber () {
    	for (int i = 0; i < blockNumbers.length; i++) {
			if (blockNumbers[i] == 0) 
				{
					blockNumbers[i] = 1;
					return i;
				}
		}
		return -1;
    }
	
	public static void main(String[] args) throws IOException  
    { 
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(5056 , 1, InetAddress.getLocalHost());
        Singleton.writeToFile("Server has starter on: " + ss.getInetAddress() + ", port: " + ss.getLocalPort());
//        System.out.println("Server has starter on: " + ss.getInetAddress() + ", port: " + ss.getLocalPort()); 
          
        // running infinite loop for getting 
        // client request 
        
//       for (int i = 0; i < 4; i++) {
//    	  Client.run(args);	
//       }
        
        while (true)  
        { 
            Socket s = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                  
                Singleton.writeToFile("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                Singleton.writeToFile("Assigning new thread for this client " + (clientCount + 1)); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, ++clientCount, dis, dos, T);
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread  
{ 
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    int clientNumber;
    String roll_number;
    String blockNumber;
    String S = "00";
      
  
    // Constructor 
    public ClientHandler(Socket s, int clientName, DataInputStream dis, DataOutputStream dos, String T)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
        this.clientNumber = clientName;
        this.roll_number = T;
    } 
  
//    @Override
//    public void run()  
//    { 
//        String received; 
//        String toreturn; 
//        while (true)  
//        { 
//            try { 
//  
//                // Ask user what he wants 
//                dos.writeUTF("What do you want?[Date | Time]..\n"+ 
//                            "Type Exit to terminate connection."); 
//                  
//                // receive the answer from client 
//                received = dis.readUTF(); 
//                  
//                if(received.equals("Exit")) 
//                {  
//                    System.out.println("Client " + this.s + " sends exit..."); 
//                    System.out.println("Closing this connection."); 
//                    this.s.close(); 
//                    System.out.println("Connection closed"); 
//                    break; 
//                } 
//                  
//                // creating Date object 
//                Date date = new Date(); 
//                  
//                // write on output stream based on the 
//                // answer from the client 
//                switch (received) { 
//                  
//                    case "Date" : 
//                    	Singleton.writeToFile("Asking for date from: " + this.clientNumber ); 
//                        toreturn = fordate.format(date); 
//                        dos.writeUTF(toreturn); 
//                        break; 
//                          
//                    case "Time" : 
//                    	Singleton.writeToFile("Asking for time from: " + this.clientNumber); 
//                        toreturn = fortime.format(date); 
//                        dos.writeUTF(toreturn); 
//                        break; 
//                          
//                    default: 
//                        dos.writeUTF("Invalid input"); 
//                        break; 
//                } 
//            } catch (IOException e) { 
//                e.printStackTrace();
//			}
//        } 
//          
//        try
//        { 
//            // closing resources 
//            this.dis.close(); 
//            this.dos.close(); 
//              
//        }catch(IOException e){ 
//            e.printStackTrace(); 
//        } 
//    } 
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	while (true) {
    		try {
    			this.blockNumber = String.valueOf((int) Server.findBlockNumber());
    			dos.writeUTF(this.roll_number);
    			dos.writeUTF(new String (this.blockNumber));
    			dis.readUTF();
    		} catch (Exception e) {
				// TODO: handle exception
			}
    		
    		if (Integer.parseInt(this.blockNumber) > 20) {
    			
    	        try
    	        { 
    	            // closing resources 
    	            this.dis.close(); 
    	            this.dos.close(); 
    	              
    	        }catch(IOException e){ 
    	            e.printStackTrace(); 
    	        } 
    			break;
    		}
    	}
    	try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
} 