import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
public class Server extends Thread 
{ 
	int clients;
	public Server (int clientN) {
		this.clients = clientN;
	}
	
    public static int clientCount = 0;
    public static int blockNumbers [] = new int [1024];
    public static long startTime;
    
    //set roll number text
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
	
    @Override
	public void run()
    { 
        
        Singleton.removeFile();
        
		// server is listening on port 5056 
        ServerSocket ss = null;
		try {
			ss = new ServerSocket(5056 , 1, InetAddress.getLocalHost());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("Server has starter on: " + ss.getInetAddress() + ", port: " + ss.getLocalPort());
//        System.out.println("Server has starter on: " + ss.getInetAddress() + ", port: " + ss.getLocalPort()); 
          
        // running infinite loop for getting 
        // client request 
        
        int cnt = 0;
        
        while (true)
        {
            Socket s = null; 
              
            try 
            { 
                // socket object to receive incoming client requests
                s = ss.accept();
                  
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
//                Singleton.writeToFile("Assigning new thread for this client " + (clientCount + 1)); 
  
                // create a new thread object
                Thread t = new ClientHandler(s, ++clientCount, dis, dos, T);
  
                // Invoking the start() method
                t.start();
            } 
            catch (Exception e){ 
                try {
                	System.out.println("closing socket!");
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
                e.printStackTrace(); 
            } 
        }
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread  
{ 
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
    
    @Override
    public void run() {
    	// TODO Auto-generated method stub
    	while (true) {
    		try {
    			this.blockNumber = String.valueOf((int) Server.findBlockNumber());

        		if (Integer.parseInt(this.blockNumber) == 0) {
        			System.out.println("First Block!");
        			Server.startTime = System.currentTimeMillis();
        		}
    			
        		if (Integer.parseInt(this.blockNumber) == -1) {
        			
        			try
        	        { 
        	            // closing resources 
        	        	dos.writeUTF("END");
        	            this.dis.close(); 
        	            this.dos.close(); 
        	              
        	        }catch(IOException e){ 
        	            e.printStackTrace(); 
        	        } 
        			break;
        		}
        		
    			dos.writeUTF(this.roll_number);
    			dos.writeUTF(new String (this.blockNumber));
    			
    			String received = dis.readUTF();
    			
    			while (!received.equals("END")) {
    				Singleton.writeToFile("[client-"+ this.clientNumber + "]" + received);
    				received = dis.readUTF();
    			}
    			
    			System.out.println("Block End ClientNumber: " + this.clientNumber + " ,Block Number: " + this.blockNumber);
    			
        		if (Integer.parseInt(this.blockNumber) == Server.blockNumbers.length - 1) {
        			
        	        System.out.println("Finished!");
        	        
        	        long elapsedTime = System.currentTimeMillis() - Server.startTime;
        	        Singleton.writeToFile("Time = " + String.valueOf(elapsedTime) + " miliseconds.");  
        		}
    			
    		} catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	
    }
} 