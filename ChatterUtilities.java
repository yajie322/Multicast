import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class ChatterUtilities{
	private MulticastSocket chatSocket;
	private MulticastSocket gameSocket;
	private int PORT;
	private int ttl = 64; /* time to live */
	private InetAddress chatGroup; 
	private InetAddress gameGroup;
	private String chatAddress;
	private String gameAddress;
	private String name;
	public int target;
	public String fromKeyboard;
	public boolean quit;
	public boolean changeRoom;
	
	public ChatterUtilities(PeerData peerData)throws Exception{
		this.PORT = peerData.getPort();
		this.chatAddress = peerData.chatAddress;
		this.gameAddress = peerData.getGroupAddress();
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.chatSocket = new MulticastSocket(PORT);
		this.gameSocket = new MulticastSocket(PORT);
		/* set the time to live */
		chatSocket.setTimeToLive(ttl);
		this.chatGroup = InetAddress.getByName(chatAddress);
		this.quit = false;
		this.changeRoom = false;
	}
	public void setName() throws Exception {
	    System.out.print("Please enter a screen name by ");
		name = readFromKeyboard();
	}

	public String getName() throws Exception {
	    return name;
	}
	
	public void joinChatGroup() throws Exception {
		InetAddress IP=InetAddress.getLocalHost();
        chatSocket.setInterface(IP);
		chatGroup = InetAddress.getByName(chatAddress);
		chatSocket.joinGroup(chatGroup);
	}
	
	public void leaveChatGroup() throws Exception {
		chatSocket.leaveGroup(chatGroup);
		chatSocket.close();
	}
	
	public void joinGameGroup() throws Exception {
		InetAddress IP=InetAddress.getLocalHost();
        gameSocket.setInterface(IP);
		gameGroup = InetAddress.getByName(gameAddress);
		gameSocket.joinGroup(gameGroup);
	}
	
	public void leaveGameGroup() throws Exception {
		gameSocket.leaveGroup(gameGroup);
		gameSocket.close();
	}

	public  String readFromKeyboard() throws Exception {
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter text: ");
		sendString = stdin.readLine();
		this.fromKeyboard = sendString;
		return sendString;
	}
	
	public void sendToSocket(String msg) throws Exception{
		/* remember to convert keyboard input (in msg) to bytes */
		 DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.length(),chatGroup, PORT);
		 chatSocket.send(sendPacket);
	}
	
	public String readFromSocket() throws Exception{
		String socketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		 byte[] buf = new byte[1000];
		 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		 chatSocket.receive(recv);
		 socketString = new String(recv.getData(), 0, recv.getLength());
		return socketString;	
	}
	
	public String readFromGameSocket() throws Exception{
		String socketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		 byte[] buf = new byte[1000];
		 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		 gameSocket.receive(recv);
		 socketString = new String(recv.getData(), 0, recv.getLength());
		return socketString;	
	}
	
	public void sendToTerminal(String msg) throws Exception{
		//System.out.println("Multicast text: " + msg);
		System.out.println(msg);
	}
	
}
