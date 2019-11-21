package DHT;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetSocketAddress;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class DHTClient {
    private static final Logger logger = Logger.getLogger(DHTClient.class.getName());
    private final ManagedChannel channel;
    private final DHTRpcGrpc.DHTRpcBlockingStub blockingStub;

    public DHTClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
    }

    /** Construct client for accessing HelloWorld server using the existing channel. */
    DHTClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = DHTRpcGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void findSuccessor(int id) {
        logger.info("Will try to contact " + id + " ...");
        FindSuccessorRequest request = FindSuccessorRequest.newBuilder().setId(id).build();
        FindSuccessorReply reply;
        try {
            reply = blockingStub.findSuccessor(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        System.out.println("Received " + reply.getAddress() + ":" +reply.getPort());
    }

    public static void main(String[] args) throws Exception {
        DHTClient client = new DHTClient("localhost", 9001);
        try {
            String user = "world";
            // Use the arg as the name to greet if provided
            if (args.length > 0) {
                user = args[0];
            }
            client.findSuccessor(123);
            } finally {
                client.shutdown();
        }
    }
}