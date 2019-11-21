package DHT;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;
import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class DHTServer {
    

    public static final int M = 32;

    private long ID;
    private InetSocketAddress localAddress;
    private InetSocketAddress predecessor;
    private InetSocketAddress successor;
	private HashMap<Integer, InetSocketAddress> finger;

    public static void main(String[] args) {
        System.out.println("Server");
    }
    
}