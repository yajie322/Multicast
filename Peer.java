import java.net.*;
import java.io.*;

public class Peer {
	public static void main(String args[]) throws Exception {
		System.setProperty("java.net.preferIPv4Stack", "true");
		if (args.length != 2) {
			throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		}
		
		InetAddress serverAddress = InetAddress.getByName(args[0]); // Server Address
		int servPort = Integer.parseInt(args[1]); // Port #
		
		String toSend = null;
		PeerData toRecv = null;
		
		Socket socket = new Socket(serverAddress, servPort);
		
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.flush();
		toSend = "Play";
		oos.writeObject(toSend);
		oos.flush();
		
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		try {
			toRecv = (PeerData)ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		if (toRecv != null) {
			System.out.println(toRecv.toString());
		}
		
		
		
		PeerSendThread PST;
		PeerReceiveThread PRT;
		PeerUtilities peerU = new PeerUtilities(toRecv);
		peerU.setName();
		peerU.joinGroup();
		
		//while (!peerU.getGameOver()) {
		if (peerU.getPlayerNumber() == 1) System.out.println("Enter a number from 1 to 50 for the other player to guess:");
			PST = new PeerSendThread(peerU);
			Thread T = new Thread(PST);
			T.setDaemon(true);
			T.start();
			PRT = new PeerReceiveThread(peerU);
			Thread TT = new Thread(PRT);
			TT.start();
			TT.join();
		//}
		peerU.leaveGroup();
		
		
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		oos.flush();
		toSend = "bye";
		oos.writeObject(toSend);
		oos.flush();
		socket.close();
	} // main
} // class PEER
