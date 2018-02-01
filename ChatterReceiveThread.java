
public class ChatterReceiveThread implements Runnable{
	ChatterUtilities chatU;
	public ChatterReceiveThread(ChatterUtilities chatU){
		this.chatU = chatU;
	}
	public void run(){
		String fromSocket = null;
		try {
			while (true) {
				fromSocket = chatU.readFromSocket();
				if (chatU.changeRoom) break;
				//System.out.println(Thread.currentThread().getName() + " receiving");
				System.out.println(chatU.getName() + " receiving");
				chatU.sendToTerminal(fromSocket);
			}

		} catch (Exception E) {
		}
	}	
}
