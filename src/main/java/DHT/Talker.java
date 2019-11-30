package DHT;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Talker thread that processes request accepted by listener and writes
 * response to socket.
 *
 */

public class Talker implements Runnable{

	private DHTNode local;
    private RPCServer server;

	public Talker(DHTNode _local) {
        local = _local;
        // System.out.println("Talker Constructor");
	}

	public void run() {
		try {
			this.server = new RPCServer(local);
		} catch (IOException e) {
			throw new RuntimeException(
					"Cannot initiate RPC server", e);
		}
	}

	public void shutdown() {
        this.server.stop();
    }
}
