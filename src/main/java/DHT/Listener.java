package DHT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listener thread that keeps listening to a port and asks talker thread to process
 * when a request is accepted.
 *
 */

public class Listener extends Thread {

	private DHTNode local;
	private boolean alive;
	private RPCServer server;

	public Listener (DHTNode n) {
		local = n;
		alive = true;
		InetSocketAddress localAddress = local.getAddress();
        int port = localAddress.getPort();
	}

	@Override
	public void run() {
		//new Thread(new Talker(local)).start();
		try {
			this.server = new RPCServer(local);
		} catch (IOException e) {
			throw new RuntimeException("Cannot initiate RPC server", e);
		}
	}

	public void toDie() {
		alive = false;
		if (this.server != null) {
            this.server.stop();
        }
	}
}
