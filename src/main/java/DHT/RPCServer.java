package DHT;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.protobuf.Empty;

import java.net.*;
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

    public void stop() {
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
        public void iAmPreRPC(addr req, StreamObserver<empty> responseObserver) {
            // System.out.println(local.getAddress().getPort() + " receiving from " + req.getPort() + " iAmPre");
            InetAddress ip = null;
            try {
                ip = InetAddress.getByName(req.getAddress());
                InetSocketAddress newPre = new InetSocketAddress(ip, req.getPort());
                local.notified(newPre);
			} catch (UnknownHostException e) {
				System.out.println("Cannot create ip address: " + req.getAddress());
			}
            
            empty reply = empty.newBuilder().build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            // System.out.println(local.getAddress().getPort() + " received from " + req.getPort() + " iAmPre");
        }

        @Override
        public void findSuccessorRPC(findSuccessorRequest req, StreamObserver<addr> responseObserver) {
            // System.out.println(local.getAddress().getPort() + " receiving from " + req.getId() + " findSuccessor");
            long id = req.getId();
            InetSocketAddress succ = local.find_successor(id);
            String succIp = Helper.getIpString(succ);
            addr reply = addr.newBuilder().setAddress(succIp).setPort(succ.getPort()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            // System.out.println(local.getAddress().getPort() + " received from " + req.getId() + " findSuccessor");
        } 
        
        @Override
        public void yourSuccessorRPC(empty req, StreamObserver<addr> responseObserver) {
            // System.out.println(local.getAddress().getPort() + " receiving from " + "client" + " yourSuccessor");
            InetSocketAddress succ = null;
            succ = local.getSuccessor();
            addr reply;
            if(succ != null) {
                reply = addr.newBuilder()
                            .setFlag(true)
                            .setAddress(Helper.getIpString(succ))
                            .setPort(succ.getPort())
                            .build();
            }
            else {
                reply = addr.newBuilder()
                            .setFlag(false)
                            .build();
            }
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            // System.out.println(local.getAddress().getPort() + " received from " + "client" + " yourSuccessor");
        }

        @Override
        public void yourPredecessorRPC(empty req, StreamObserver<addr> responseObserver) {
            // System.out.println(local.getAddress().getPort() + " receiving from " + "client" + " yourPredecessor");
            InetSocketAddress pred = null;
            pred = local.getPredecessor();
            addr reply;
            if(pred != null) {
                reply = addr.newBuilder()
                            .setFlag(true)
                            .setAddress(Helper.getIpString(pred))
                            .setPort(pred.getPort())
                            .build();
            }
            else {
                reply = addr.newBuilder()
                            .setFlag(false)
                            .build();
            }
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            // System.out.println(local.getAddress().getPort() + " received from " + "client" + " yourPredecessor");
        }

        @Override
        public void closestPrecedingFingerRPC(closestPrecedingFingerRequest req, StreamObserver<addr> responseObserver) {
            // System.out.println(local.getAddress().getPort() + " receiving from " + req.getId() + " closestPrecedingFinger");
            addr reply;
            long id = req.getId();
            InetSocketAddress result = local.closest_preceding_finger(id);
            reply = addr.newBuilder().setAddress(Helper.getIpString(result)).setPort(result.getPort()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            // System.out.println(local.getAddress().getPort() + " received from " + req.getId() + " closestPrecedingFinger");
        }

        @Override 
        public void keepAliveRPC(empty req, StreamObserver<keepAliveReply> responseObserver) {
            // System.out.println(local.getAddress().getPort() + " receiving from " + "client" + " keepAlive");
            keepAliveReply reply;
            reply = keepAliveReply.newBuilder().setFlag(true).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            // System.out.println(local.getAddress().getPort() + " received from " + "client" + " keepAlive");
        }

        @Override
        public void getIdRPC(empty req, StreamObserver<getIdReply> responseObserver) {
            getIdReply reply;
            reply = getIdReply.newBuilder().setId(local.getId()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

    }
    
}