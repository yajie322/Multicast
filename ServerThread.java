import java.net.*;
import java.io.*;

public class ServerThread implements Runnable {
	private Socket socket;
	private InputStream recvStream;
	private OutputStream sendStream;
	private PeerData toSend;
	private String toRecv;
	private RoomManager RM;
	private boolean isPlayer;

	public ServerThread(Socket clntSock, RoomManager rm) throws IOException {
		this.socket = clntSock;
		this.recvStream = clntSock.getInputStream();
		this.sendStream = clntSock.getOutputStream();
		this.RM = rm;
		this.isPlayer = false;
	}

	public void run() {
		int room;
		try {
			ObjectInputStream ois = new ObjectInputStream(recvStream);  
			toRecv = (String)ois.readObject();
			if (toRecv.equals("Play")) {
				room = RM.getNextGameRoom();
				toSend = new PeerData(RM.GameRooms[room], RM.ChatRooms[room], RM.ports[room], RM.numOfPlayers % 2 + 1);
				RM.numOfPlayers++;
				RM.playersInRoom[room]++;
				isPlayer = true;
				
				ObjectOutputStream oos = new ObjectOutputStream(sendStream);  
				oos.writeObject(toSend);
				
				ois = new ObjectInputStream(recvStream);  
				toRecv = (String)ois.readObject();
				if (toRecv.equals("bye")) {
					if (isPlayer) {
						RM.numOfPlayers--;
						RM.playersInRoom[room]--;
					}
					System.out.println("Server Thread [" + Thread.currentThread().getName() + "] is now gone!");
					socket.close();
					sendStream.close();
					recvStream.close();
				}
			}
			else {//if (toRecv.equals("Chat")) {
				while (!toRecv.equals("bye")) {
				room = RM.getNextChatRoom();
				if (room != -1) {
					toSend = new PeerData(RM.GameRooms[room], RM.ChatRooms[room], RM.ports[room], 0);
				} else {
					toSend = new PeerData("224.1.1.100","224.1.2.100", 10001,0);
				}
				ObjectOutputStream oos = new ObjectOutputStream(sendStream);  
				oos.writeObject(toSend); 
				
				ois = new ObjectInputStream(recvStream);  
				toRecv = (String)ois.readObject();
			
				}
				System.out.println("Server Thread [" + Thread.currentThread().getName() + "] is now gone!");
				socket.close();
				sendStream.close();
				recvStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
