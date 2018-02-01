
public class PeerReceiveThread implements Runnable{
	PeerUtilities peerU;
	public PeerReceiveThread(PeerUtilities peerU){
		this.peerU = peerU;
	}
	public void run(){
		String fromSocket = null;
		String toSend = null;
		try {
			while (true) {
				fromSocket = peerU.readFromSocket();
				//System.out.println(Thread.currentThread().getName() + " receiving");
				//System.out.println(peerU.getName() + " receiving");
				peerU.sendToTerminal(fromSocket);
				
				if (fromSocket.contains("bye")) {
					break;
				}
				
				if (fromSocket.contains("You win! Switch Side.")) {
					peerU.switchPlayerNumber();
					if (peerU.getPlayerNumber() == 1) {
						System.out.println("Enter a number from 1 to 50 for the other player to guess:");
						peerU.requestNumber = true;
					}
					continue;
				}
				
				if (fromSocket.contains(peerU.getName())) continue;

				
				if (peerU.getPlayerNumber() == 1) { // for player 1
					try {
						String[] temp = fromSocket.split(" ");
						int guess = Integer.parseInt(temp[temp.length-1]);
						if (guess < peerU.target) toSend = "Too Low. Guess again!";
						else if (guess > peerU.target) toSend = "Too High. Guess again!";
						else toSend = "You win! Switch Side.";
						peerU.sendToSocket(peerU.getName() + " sent: " +toSend);
					} catch (NumberFormatException e) {
			    			peerU.sendToSocket(peerU.getName() + " sent: " +"Please enter an integer between 1 and 50!");
					}
				}
				
				
			}

		} catch (Exception E) {
		}
	}	
}
