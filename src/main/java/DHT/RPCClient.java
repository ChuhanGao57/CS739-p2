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
public class RPCClient {
    private static final Logger logger = Logger.getLogger(RPCClient.class.getName());
    private final ManagedChannel channel;
    private final DHTRpcGrpc.DHTRpcBlockingStub blockingStub;
    private DHTNode local;
    private InetSocketAddress serverAddr;

    public RPCClient(DHTNode _local, String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
        local = _local;
        serverAddr = new InetSocketAddress(host, port);
    }

    /** Construct client for accessing HelloWorld server using the existing channel. */
    RPCClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = DHTRpcGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void findSuccessor(long id) {
        //System.out.println("Will try to contact " + id + " ...");
        findSuccessorRequest request = findSuccessorRequest.newBuilder().setId(id).build();
        addr reply;
        try {
            reply = blockingStub.findSuccessorRPC(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "findSuccessor RPC failed: {0}", e.getStatus());
            return;
        }
        System.out.println("Received " + reply.getAddress() + ":" +reply.getPort());
    }

    public void iAmPre() {
        String host = local.getAddress().getHostName();
        int port = local.getAddress().getPort();
        addr request = addr.newBuilder().setAddress(host).setPort(port).build();
        empty reply;
        System.out.println(local.getAddress().getPort() + " telling " + serverAddr.getPort() + " IAmPre");
        try {
            reply = blockingStub.iAmPreRPC(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "IAmPre RPC failed: {0}", e.getStatus());
            return;
        }
        System.out.println(local.getAddress().getPort() + " told " + port + " IAmPre");
    }



    public static void main(String[] args) throws Exception {
        // RPCClient client = new RPCClient("localhost", 9001);
        // try {
        //     String user = "world";
        //     // Use the arg as the name to greet if provided
        //     if (args.length > 0) {
        //         user = args[0];
        //     }
        //     client.findSuccessor(123);
        //     } finally {
        //         client.shutdown();
        // }
    }
}