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
    private static final Logger logger = Logger.getLogger(DHTServer.class.getName());

    public static final int M = 32;

    private long ID;
    private InetSocketAddress localAddress;
    private InetSocketAddress predecessor;
    // 他的实现把successor放在finger第一个位置了，这个地方看你
    private InetSocketAddress successor;
	private HashMap<Integer, InetSocketAddress> finger;

    private FixFingers fixFingers;
    private Stabilize stabilize;

    private Server server;

    public DHTServer(String hostname, int port) {
        localAddress = new InetSocketAddress(hostname, port);
    }

    public boolean join(InetSocketAddress contact) {
        return false;
    }

    public synchronized void updateFingers(int index, InetSocketAddress addr) {

    }
    
    // example usage? maybe a unit test
    public InetSocketAddress findSuccessor(long id) {
        DHTClient client = new DHTClient("localhost", localAddress.getPort());
        return client.findSuccessor((int)id);
    }

    public InetSocketAddress closestPrecedingFinger (long id) {
        return null;
    }
    
    public void notify (InetSocketAddress successor) {
        
    }


    public void start() throws IOException {
        /* The port on which the server should run */
        int port = localAddress.getPort();
        server = ServerBuilder.forPort(port)
                .addService(new DHTRpcImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                DHTServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}