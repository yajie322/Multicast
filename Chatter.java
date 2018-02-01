import java.net.*;
import java.io.*;

public class Chatter {
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
		toSend = "Chat";
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
		
		ChatterSendThread CST;
		ChatterReceiveThread CRT;
		ChatterListenThread CLT;
		while (true) {
			ChatterUtilities chatU = new ChatterUtilities(toRecv);
			chatU.setName();
			chatU.joinChatGroup();
			chatU.joinGameGroup();
		
			CST = new ChatterSendThread(chatU);
			Thread T = new Thread(CST);
			T.start();
			CRT = new ChatterReceiveThread(chatU);
			Thread TT = new Thread(CRT);
			TT.setDaemon(true);
			TT.start();
			CLT = new ChatterListenThread(chatU);
			Thread TTT = new Thread(CLT);
			TTT.setDaemon(true);
			TTT.start();
			T.join();
			chatU.leaveGameGroup();
			chatU.leaveChatGroup();
			if (chatU.quit) break;
		
			oos = new ObjectOutputStream(os);
			oos.flush();
			toSend = "Chat";
			oos.writeObject(toSend);
			oos.flush();
		
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			try {
				toRecv = (PeerData)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
			if (toRecv != null) {
				System.out.println(toRecv.toString());
			}
		
		}
		
		
		os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		oos.flush();
		toSend = "bye";
		oos.writeObject(toSend);
		oos.flush();
		socket.close();
	}
}