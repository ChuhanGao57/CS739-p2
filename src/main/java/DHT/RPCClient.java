package DHT;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.Empty;

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

    public RPCClient(InetSocketAddress _serverAddr) {
        this(null, _serverAddr);
    }

    public RPCClient(DHTNode _local, String host, int port) {
        this(ManagedChannelBuilder.forAddress("127.0.0.1", port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
        local = _local;
        serverAddr = new InetSocketAddress("localhost", port);
    }

    public RPCClient(DHTNode _local, InetSocketAddress _serverAddr) {
        this(ManagedChannelBuilder.forAddress(_serverAddr.getAddress().getHostAddress() , _serverAddr.getPort())
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
        local = _local;
        serverAddr = _serverAddr;
    }

    /** Construct client for accessing HelloWorld server using the existing channel. */
    RPCClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = DHTRpcGrpc.newBlockingStub(channel);
    }

    public void shutdown() {
        channel.shutdown();
        // channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void iAmPre() {
        if(local == null) {
            System.out.println("WARNING: DHT Node is null. Cannot make iAmPre RPC call!");
            return;
        }
        // String host = local.getAddress().getHostName();
        String host = local.getIpString();
        int port = local.getAddress().getPort();
        addr request = addr.newBuilder().setAddress(host).setPort(port).build();
        empty reply;
         // System.out.println(local.getAddress().getPort() + " sending " + serverAddr.getPort() + " IAmPre");
        try {
            reply = blockingStub.iAmPreRPC(request);
        } catch (StatusRuntimeException e) {
            
            logger.log(Level.WARNING, "IAmPre RPC failed: {0}", e.getStatus());
            //System.out.println("IAmPre RPC failed");
            return;
        }
         // System.out.println(local.getAddress().getPort() + " sent " + serverAddr.getPort() + " IAmPre");
    }


    public InetSocketAddress findSuccessor(long id) {
        // System.out.println(local.getAddress().getPort() + " sending " + serverAddr.getPort() + " findSuccessor");
        findSuccessorRequest request = findSuccessorRequest.newBuilder().setId(id).build();
        addr reply;
        try {
            reply = blockingStub.findSuccessorRPC(request);
            // System.out.println(local.getAddress().getPort() + " sent " + serverAddr.getPort() + " findSuccessor");
            // return new InetSocketAddress(reply.getAddress(), reply.getPort());
            return Helper.createSocketAddress(reply.getAddress(), reply.getPort());
            
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "findSuccessor RPC failed: {0}", e.getStatus());
            return null;
        }
        
    }

    

    public InetSocketAddress yourSuccessor() {
        empty request = empty.newBuilder().build();
        addr reply;
        // System.out.println(local.getAddress().getPort() + " sending " + serverAddr.getPort() + " youSuccessor");
        try {
            reply = blockingStub.yourSuccessorRPC(request);
            // System.out.println(local.getAddress().getPort() + " sent " + serverAddr.getPort() + " youSuccessor");
            if(reply.getFlag()) {
                // return new InetSocketAddress(reply.getAddress(), reply.getPort());
                return Helper.createSocketAddress(reply.getAddress(), reply.getPort());
            }
                
            else
                return serverAddr; // Attention
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "yourSuccessor RPC failed: {0}", e.getStatus());
            return null;
        }
        
    }


    public InetSocketAddress closestPrecedingFinger(long id) {
        closestPrecedingFingerRequest request = closestPrecedingFingerRequest.newBuilder()
                                                .setId(id).build();
        addr reply;
        // System.out.println(local.getAddress().getPort() + " sending " + serverAddr.getPort() + " closestPrecedingFinger");
        try {
            reply = blockingStub.closestPrecedingFingerRPC(request);
            // System.out.println(local.getAddress().getPort() + " sent " + serverAddr.getPort() + " closestPrecedingFinger");
            // return new InetSocketAddress(reply.getAddress(), reply.getPort());\
            return Helper.createSocketAddress(reply.getAddress(), reply.getPort());
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "closestPrecedingFinger RPC failed: {0}", e.getStatus());
            return null;
        }
    }


    public boolean keepAlive() {
        empty request = empty.newBuilder().build();
        keepAliveReply reply;
        // System.out.println(local.getAddress().getPort() + " sending " + serverAddr.getPort() + " keepAlive");
        try {
            reply = blockingStub.keepAliveRPC(request);
            // System.out.println(local.getAddress().getPort() + " sent " + serverAddr.getPort() + " keepAlive");
            return reply.getFlag();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "keepAlive RPC failed: {0}", e.getStatus());
            return false;
        }
    }

    public InetSocketAddress yourPredecessor() {
        empty request = empty.newBuilder().build();
        addr reply;
        // System.out.println(local.getAddress().getPort() + " sending " + serverAddr.getPort() + " yourPredecessor");
        try {
            reply = blockingStub.yourPredecessorRPC(request);
            // System.out.println(local.getAddress().getPort() + " sent " + serverAddr.getPort() + " yourPredecessor");
            if(reply.getFlag()) 
                return Helper.createSocketAddress(reply.getAddress(), reply.getPort());
            else
                return serverAddr; // Attention
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "yourPredecessor RPC failed: {0}", e.getStatus());
            return null;
        }
    }

    public long getId() {
        empty request = empty.newBuilder().build();
        getIdReply reply;
        try {
            reply = blockingStub.getIdRPC(request);
            return reply.getId();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "getId RPC failed: {0}", e.getStatus());
            return -1;
        }
    }

    public void killNode() {
        empty request = empty.newBuilder().build();
        empty reply;
        try {
            reply = blockingStub.killNodeRPC(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "killNode RPC failed: {0}", e.getStatus());
            return;
        }
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