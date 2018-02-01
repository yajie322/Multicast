import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class PeerUtilities{
	private MulticastSocket socket;
	private int PORT;
	private int ttl = 64; /* time to live */
	private InetAddress group; 
	private String IPadrress;
	private String name;
	private int playerNumber;
	public int target;
	public String fromKeyboard;
	public boolean requestNumber;
	
	public PeerUtilities(PeerData peerData)throws Exception{
		this.PORT = peerData.getPort();
		this.IPadrress = peerData.getGroupAddress();
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.socket = new MulticastSocket(PORT);
		/* set the time to live */
		socket.setTimeToLive(ttl);
		this.group = InetAddress.getByName(IPadrress);
		this.playerNumber = peerData.getPlayerNumber();
		this.requestNumber = (this.playerNumber == 1);
	}
	public void setName() throws Exception {
	    System.out.print("Please enter a screen name by ");
		name = readFromKeyboard();
	}

	public String getName() throws Exception {
	    return name;
	}
	
	public void switchPlayerNumber() throws Exception {
		playerNumber = playerNumber % 2 + 1;
		
	}
	
	public int getPlayerNumber() throws Exception {
		return playerNumber;
	}
	
	public void joinGroup() throws Exception {
		InetAddress IP=InetAddress.getLocalHost();
        socket.setInterface(IP);
		group = InetAddress.getByName(IPadrress);
		socket.joinGroup(group);
	}
	public void leaveGroup() throws Exception {
		socket.leaveGroup(group);
		socket.close();
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
		 DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.length(),group, PORT);
		 socket.send(sendPacket);
	}
	
	public String readFromSocket() throws Exception{
		String socketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		 byte[] buf = new byte[1000];
		 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		 socket.receive(recv);
		 socketString = new String(recv.getData(), 0, recv.getLength());
		return 	socketString;	
	}
	public void sendToTerminal(String msg) throws Exception{
		//System.out.println("Multicast text: " + msg);
		System.out.println(msg);
	}
	
}
