
public class RoomManager {
	public int numOfRooms;
	public String[] GameRooms;
	public String[] ChatRooms;
	public int[] ports;
	public int[] playersInRoom;
	public int numOfPlayers;
	public int currentChatRoom;

	public RoomManager(int n) {
		this.numOfRooms = n;
		this.GameRooms = new String[n];
		this.ChatRooms = new String[n];
		this.ports = new int[n];
		this.playersInRoom = new int[n];
		for (int i = 0; i < n; i++) {
			GameRooms[i] = "224.1.1." + i;
			ChatRooms[i] = "224.1.2." + i;
			ports[i] = 10000 + i;
		}
		this.numOfPlayers = 0;
		this.currentChatRoom = 0;
	}
	
	public int getNextGameRoom() {
		for (int i = 0; i < numOfRooms; i++) {
			if (playersInRoom[i] < 2) {
				return i;
			}
		}
		return -1;
	}
	
	public int getNextChatRoom() {
		for (int i = 0; i < numOfRooms; i++) {
			if (playersInRoom[currentChatRoom] == 2) {
				return currentChatRoom++;
			}
			currentChatRoom++;
			if (currentChatRoom >= numOfRooms) {
				currentChatRoom = 0;
			}
		}
		return -1;
	}
}
