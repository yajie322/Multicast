public class PeerSendThread implements Runnable {
	PeerUtilities peerU;

	public PeerSendThread(PeerUtilities peerU) {
		this.peerU = peerU;
	}

	public void run() {
		String fromKeyboard = null;
		String message = null;
		try {
			while (true) {
				fromKeyboard = peerU.readFromKeyboard();
				if (peerU.requestNumber) {
					try {
						peerU.target = Integer.parseInt(fromKeyboard);
						peerU.requestNumber = false;
						peerU.sendToSocket(peerU.getName() + " sent: " +"Time to play Hi/Low. Please enter a number between 1 and 50.");
					} catch (NumberFormatException e) {
						System.out.println("Please enter an integer between 1 and 50!");
					}
					continue;
				}
				//System.out.println(Thread.currentThread().getName() + " sending");
				message = peerU.getName() + " sent: " + fromKeyboard;
				//System.out.println(peerU.getName() + " sending");
				peerU.sendToSocket(message);
				//fromSocket = peerU.readFromSocket();
				//peerU.sendToTerminal(fromSocket);
			}

		} catch (Exception E) {
		}
	}
}
