package DHT;

import io.grpc.stub.StreamObserver;


class DHTRpcImpl extends DHTRpcGrpc.DHTRpcImplBase {
    DHTServer server;

    @Override
    public void findSuccessor (FindSuccessorRequest req, StreamObserver<FindSuccessorReply> responseObserver) {
        FindSuccessorReply reply = FindSuccessorReply.newBuilder().setAddress("127.0.0.1").setPort(8888).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
 }