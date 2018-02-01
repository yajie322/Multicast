public class ChatterSendThread implements Runnable {
	ChatterUtilities chatU;

	public ChatterSendThread(ChatterUtilities chatU) {
		this.chatU = chatU;
	}

	public void run() {
		String fromKeyboard = null;
		String message = null;
		try {
			while (true) {
				fromKeyboard = chatU.readFromKeyboard();
				if (fromKeyboard.equalsIgnoreCase("bye")) {
					chatU.quit = true;
					break;
				}
				if (fromKeyboard.equalsIgnoreCase("change room")) {
					chatU.changeRoom = true;
					break;
				}
				//System.out.println(Thread.currentThread().getName() + " sending");
				message = chatU.getName() + " sent: " + fromKeyboard;
				//System.out.println(peerU.getName() + " sending");
				chatU.sendToSocket(message);
				//fromSocket = peerU.readFromSocket();
				//peerU.sendToTerminal(fromSocket);
			}
		} catch (Exception E) {
		}
	}
}
