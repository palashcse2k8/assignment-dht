import java.io.IOException;
import java.util.Scanner;

public class DriverClass {
	public static void main (String args []) throws IOException, InterruptedException {
		
		Scanner sc = new Scanner(System.in);
		Server myServer = new Server(2);
		myServer.start();
		Thread.sleep(2000);
		System.out.println("Input Number of Clients to run: ");
		int clientNumber = sc.nextInt();
		Singleton.writeToFile("Number of Clients = " + clientNumber);
		long startTime = System.nanoTime();
		
		for (int i = 0; i < clientNumber; i++) {
			Client clients = new Client();
			Thread.sleep(1000);
//			System.out.println("creating client : " + i);
			clients.start();
		}
		long elapsedtime = System.nanoTime() - startTime;		
	}
}
