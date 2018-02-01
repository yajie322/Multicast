import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
import java.net.*;
public class Server {
	
    public static void main(String[] args) throws IOException {
    		System.setProperty("java.net.preferIPv4Stack", "true");
    		// Get port number
    		if (args.length != 1) 
    			throw new IllegalArgumentException("Parameter(s): <Port>");
    		int servPort = Integer.parseInt(args[0]);
    		
    		boolean listening = true;
  
    		ServerSocket serverSocket = new ServerSocket(servPort);
    		
    		RoomManager rm = new RoomManager(5);
		
        System.out.println("Server is up and running.....");
		while (listening)
		{
			Socket clntSock = serverSocket.accept();  //accept the incoming call, and pass the NEW socket to the thread
			ServerThread servThread = new ServerThread(clntSock, rm);
			Thread T = new Thread(servThread);
			T.start();
		}
		serverSocket.close();
    }
}
