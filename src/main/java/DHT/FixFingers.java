package DHT;

import java.net.InetSocketAddress;

public class FixFingers extends Thread{

	private DHTServer server;

	public FixFingers (DHTServer server) {
		this.server = server;
	}

	@Override
	public void run() {
        //Implement here
	}
}
