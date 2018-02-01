import java.io.Serializable;

public class PeerData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String groupAddress;
	public String chatAddress;
	private int PORT;
	private int playerNumber;
	
	public PeerData(String GRaddress, String CRaddress, int port, int playernumber) {
		this.groupAddress = GRaddress;
		this.chatAddress = CRaddress;
		this.PORT = port;
		this.playerNumber = playernumber;
	}
	
	public String getGroupAddress() {
		return this.groupAddress;
	}
	
	public int getPort() {
		return this.PORT;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public String toString() {
		return "Game Room Address: " + getGroupAddress() + " Chat Room Address: " + chatAddress +", Port: " + getPort() + ", playerNumber: " + getPlayerNumber();
	}
}
