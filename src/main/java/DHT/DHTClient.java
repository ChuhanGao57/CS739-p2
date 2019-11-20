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

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
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

    /** Say hello to server. */
    public InetSocketAddress findSuccessor(int id) {
        logger.info("Will try to findSuccessor " + Integer.toString(id) + " ...");
        FindSuccessorRequest request = FindSuccessorRequest.newBuilder().setId(id).build();
        FindSuccessorReply response;
        try {
            response = blockingStub.findSuccessor(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        return new InetSocketAddress(response.getAddress(), response.getPort());
        
    }
}