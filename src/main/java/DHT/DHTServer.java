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

    private Server server;

    public static final int M = 32;

    private long ID;
    private InetSocketAddress localAddress;
    private InetSocketAddress predecessor;
    private InetSocketAddress successor;
	private HashMap<Integer, InetSocketAddress> finger;


    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 9001;
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

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        System.out.println("Server");
        final DHTServer server = new DHTServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class DHTRpcImpl extends DHTRpcGrpc.DHTRpcImplBase {
        @Override
        public void findSuccessor(FindSuccessorRequest req, StreamObserver<FindSuccessorReply> responseObserver) {
            int id = req.getId();
            System.out.println("Received ID: " + id);
            FindSuccessorReply reply = FindSuccessorReply.newBuilder().setAddress("HELLO").build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }   
    }
    
}