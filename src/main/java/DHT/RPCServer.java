package DHT;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.protobuf.Empty;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class RPCServer {
    private static final Logger logger = Logger.getLogger(RPCServer.class.getName());

    private Server server;
    private InetSocketAddress address;
    private DHTNode local;

    public RPCServer(DHTNode _local) throws IOException {
        local = _local;
        address = local.getAddress();
        // System.out.println("RPC Server starting");
        this.start();
        //this.blockUntilShutdown();
    }

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = address.getPort();
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
                RPCServer.this.stop();
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

    // public static void main(String[] args) throws IOException, InterruptedException{
    //     // System.out.println("Server");
    //     // final RPCServer server = new RPCServer();
    //     // server.start();
    //     // server.blockUntilShutdown();
    // }

    class DHTRpcImpl extends DHTRpcGrpc.DHTRpcImplBase {
        @Override
        public void findSuccessorRPC(findSuccessorRequest req, StreamObserver<addr> responseObserver) {
            long id = req.getId();
            System.out.println("Received ID: " + id);
            addr reply = addr.newBuilder().setAddress("HELLO").setPort(888).build();
            //reply.setPort(8888);
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } 
        
        @Override
        public void iAmPreRPC(addr req, StreamObserver<empty> responseObserver) {
            System.out.println(local.getAddress().getPort() + " receiving from " + req.getPort() + " iAmPre");
            InetSocketAddress newPre = new InetSocketAddress(req.getAddress(), req.getPort());
            local.notified(newPre);
            empty reply = empty.newBuilder().build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            System.out.println(local.getAddress().getPort() + " received from " + req.getPort() + " iAmPre");
        }
    }
    
}