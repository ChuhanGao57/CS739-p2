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
	private ServerSocket serverSocket;
	private boolean alive;
    private RPCServer server;
	
    public Listener (DHTNode n) {
		local = n;
		alive = true;
		InetSocketAddress localAddress = local.getAddress();
        int port = localAddress.getPort();
        
        // System.out.println("Listener Constructor");

		// //open server/listener socket
		// try {
		// 	serverSocket = new ServerSocket(port);
		// } catch (IOException e) {
		// 	throw new RuntimeException("\nCannot open listener port "+port+". Now exit.\n", e);
		// }
	}

	@Override
	public void run() {
        // System.out.println("Listener Run");
		try {
			this.server = new RPCServer(local);
		} catch (IOException e) {
			throw new RuntimeException(
					"Cannot initiate RPC server", e);
		}
		// while (alive) {
			// Socket talkSocket = null;
			// try {
			// 	talkSocket = serverSocket.accept();
			// } catch (IOException e) {
			// 	throw new RuntimeException(
			// 			"Cannot accepting connection", e);
			// }

			//new talker
			// new Thread(new Talker(local)).start();
		// }
	}

	public void toDie() {
		alive = false;
        if (this.server != null) {
            this.server.stop();
        }
	}
}
