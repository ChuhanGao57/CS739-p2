package DHT;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: DHTRpc.proto")
public final class DHTRpcGrpc {

  private DHTRpcGrpc() {}

  public static final String SERVICE_NAME = "DHT.DHTRpc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<DHT.empty,
      DHT.addr> getYourSuccessorRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "yourSuccessorRPC",
      requestType = DHT.empty.class,
      responseType = DHT.addr.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.empty,
      DHT.addr> getYourSuccessorRPCMethod() {
    io.grpc.MethodDescriptor<DHT.empty, DHT.addr> getYourSuccessorRPCMethod;
    if ((getYourSuccessorRPCMethod = DHTRpcGrpc.getYourSuccessorRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getYourSuccessorRPCMethod = DHTRpcGrpc.getYourSuccessorRPCMethod) == null) {
          DHTRpcGrpc.getYourSuccessorRPCMethod = getYourSuccessorRPCMethod =
              io.grpc.MethodDescriptor.<DHT.empty, DHT.addr>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "yourSuccessorRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.addr.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("yourSuccessorRPC"))
              .build();
        }
      }
    }
    return getYourSuccessorRPCMethod;
  }

  private static volatile io.grpc.MethodDescriptor<DHT.closestPrecedingFingerRequest,
      DHT.addr> getClosestPrecedingFingerRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "closestPrecedingFingerRPC",
      requestType = DHT.closestPrecedingFingerRequest.class,
      responseType = DHT.addr.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.closestPrecedingFingerRequest,
      DHT.addr> getClosestPrecedingFingerRPCMethod() {
    io.grpc.MethodDescriptor<DHT.closestPrecedingFingerRequest, DHT.addr> getClosestPrecedingFingerRPCMethod;
    if ((getClosestPrecedingFingerRPCMethod = DHTRpcGrpc.getClosestPrecedingFingerRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getClosestPrecedingFingerRPCMethod = DHTRpcGrpc.getClosestPrecedingFingerRPCMethod) == null) {
          DHTRpcGrpc.getClosestPrecedingFingerRPCMethod = getClosestPrecedingFingerRPCMethod =
              io.grpc.MethodDescriptor.<DHT.closestPrecedingFingerRequest, DHT.addr>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "closestPrecedingFingerRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.closestPrecedingFingerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.addr.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("closestPrecedingFingerRPC"))
              .build();
        }
      }
    }
    return getClosestPrecedingFingerRPCMethod;
  }

  private static volatile io.grpc.MethodDescriptor<DHT.empty,
      DHT.addr> getYourPredecessorRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "yourPredecessorRPC",
      requestType = DHT.empty.class,
      responseType = DHT.addr.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.empty,
      DHT.addr> getYourPredecessorRPCMethod() {
    io.grpc.MethodDescriptor<DHT.empty, DHT.addr> getYourPredecessorRPCMethod;
    if ((getYourPredecessorRPCMethod = DHTRpcGrpc.getYourPredecessorRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getYourPredecessorRPCMethod = DHTRpcGrpc.getYourPredecessorRPCMethod) == null) {
          DHTRpcGrpc.getYourPredecessorRPCMethod = getYourPredecessorRPCMethod =
              io.grpc.MethodDescriptor.<DHT.empty, DHT.addr>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "yourPredecessorRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.addr.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("yourPredecessorRPC"))
              .build();
        }
      }
    }
    return getYourPredecessorRPCMethod;
  }

  private static volatile io.grpc.MethodDescriptor<DHT.findSuccessorRequest,
      DHT.addr> getFindSuccessorRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findSuccessorRPC",
      requestType = DHT.findSuccessorRequest.class,
      responseType = DHT.addr.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.findSuccessorRequest,
      DHT.addr> getFindSuccessorRPCMethod() {
    io.grpc.MethodDescriptor<DHT.findSuccessorRequest, DHT.addr> getFindSuccessorRPCMethod;
    if ((getFindSuccessorRPCMethod = DHTRpcGrpc.getFindSuccessorRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getFindSuccessorRPCMethod = DHTRpcGrpc.getFindSuccessorRPCMethod) == null) {
          DHTRpcGrpc.getFindSuccessorRPCMethod = getFindSuccessorRPCMethod =
              io.grpc.MethodDescriptor.<DHT.findSuccessorRequest, DHT.addr>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findSuccessorRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.findSuccessorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.addr.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("findSuccessorRPC"))
              .build();
        }
      }
    }
    return getFindSuccessorRPCMethod;
  }

  private static volatile io.grpc.MethodDescriptor<DHT.addr,
      DHT.empty> getIAmPreRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "iAmPreRPC",
      requestType = DHT.addr.class,
      responseType = DHT.empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.addr,
      DHT.empty> getIAmPreRPCMethod() {
    io.grpc.MethodDescriptor<DHT.addr, DHT.empty> getIAmPreRPCMethod;
    if ((getIAmPreRPCMethod = DHTRpcGrpc.getIAmPreRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getIAmPreRPCMethod = DHTRpcGrpc.getIAmPreRPCMethod) == null) {
          DHTRpcGrpc.getIAmPreRPCMethod = getIAmPreRPCMethod =
              io.grpc.MethodDescriptor.<DHT.addr, DHT.empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "iAmPreRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.addr.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.empty.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("iAmPreRPC"))
              .build();
        }
      }
    }
    return getIAmPreRPCMethod;
  }

  private static volatile io.grpc.MethodDescriptor<DHT.empty,
      DHT.keepAliveReply> getKeepAliveRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "keepAliveRPC",
      requestType = DHT.empty.class,
      responseType = DHT.keepAliveReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.empty,
      DHT.keepAliveReply> getKeepAliveRPCMethod() {
    io.grpc.MethodDescriptor<DHT.empty, DHT.keepAliveReply> getKeepAliveRPCMethod;
    if ((getKeepAliveRPCMethod = DHTRpcGrpc.getKeepAliveRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getKeepAliveRPCMethod = DHTRpcGrpc.getKeepAliveRPCMethod) == null) {
          DHTRpcGrpc.getKeepAliveRPCMethod = getKeepAliveRPCMethod =
              io.grpc.MethodDescriptor.<DHT.empty, DHT.keepAliveReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "keepAliveRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.keepAliveReply.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("keepAliveRPC"))
              .build();
        }
      }
    }
    return getKeepAliveRPCMethod;
  }

  private static volatile io.grpc.MethodDescriptor<DHT.empty,
      DHT.getIdReply> getGetIdRPCMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getIdRPC",
      requestType = DHT.empty.class,
      responseType = DHT.getIdReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<DHT.empty,
      DHT.getIdReply> getGetIdRPCMethod() {
    io.grpc.MethodDescriptor<DHT.empty, DHT.getIdReply> getGetIdRPCMethod;
    if ((getGetIdRPCMethod = DHTRpcGrpc.getGetIdRPCMethod) == null) {
      synchronized (DHTRpcGrpc.class) {
        if ((getGetIdRPCMethod = DHTRpcGrpc.getGetIdRPCMethod) == null) {
          DHTRpcGrpc.getGetIdRPCMethod = getGetIdRPCMethod =
              io.grpc.MethodDescriptor.<DHT.empty, DHT.getIdReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getIdRPC"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  DHT.getIdReply.getDefaultInstance()))
              .setSchemaDescriptor(new DHTRpcMethodDescriptorSupplier("getIdRPC"))
              .build();
        }
      }
    }
    return getGetIdRPCMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DHTRpcStub newStub(io.grpc.Channel channel) {
    return new DHTRpcStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DHTRpcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DHTRpcBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DHTRpcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DHTRpcFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class DHTRpcImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Find the node's successor
     * </pre>
     */
    public void yourSuccessorRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnimplementedUnaryCall(getYourSuccessorRPCMethod(), responseObserver);
    }

    /**
     * <pre>
     * find closest preceding finger of a given id
     * </pre>
     */
    public void closestPrecedingFingerRPC(DHT.closestPrecedingFingerRequest request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnimplementedUnaryCall(getClosestPrecedingFingerRPCMethod(), responseObserver);
    }

    /**
     * <pre>
     * find the node's predecessor
     * </pre>
     */
    public void yourPredecessorRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnimplementedUnaryCall(getYourPredecessorRPCMethod(), responseObserver);
    }

    /**
     * <pre>
     * find successor of given id
     * </pre>
     */
    public void findSuccessorRPC(DHT.findSuccessorRequest request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnimplementedUnaryCall(getFindSuccessorRPCMethod(), responseObserver);
    }

    /**
     * <pre>
     * notify i am the new predecessor to another node 
     * </pre>
     */
    public void iAmPreRPC(DHT.addr request,
        io.grpc.stub.StreamObserver<DHT.empty> responseObserver) {
      asyncUnimplementedUnaryCall(getIAmPreRPCMethod(), responseObserver);
    }

    /**
     * <pre>
     * keep alive message
     * </pre>
     */
    public void keepAliveRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.keepAliveReply> responseObserver) {
      asyncUnimplementedUnaryCall(getKeepAliveRPCMethod(), responseObserver);
    }

    /**
     * <pre>
     * get the node's id
     * </pre>
     */
    public void getIdRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.getIdReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetIdRPCMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getYourSuccessorRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.empty,
                DHT.addr>(
                  this, METHODID_YOUR_SUCCESSOR_RPC)))
          .addMethod(
            getClosestPrecedingFingerRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.closestPrecedingFingerRequest,
                DHT.addr>(
                  this, METHODID_CLOSEST_PRECEDING_FINGER_RPC)))
          .addMethod(
            getYourPredecessorRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.empty,
                DHT.addr>(
                  this, METHODID_YOUR_PREDECESSOR_RPC)))
          .addMethod(
            getFindSuccessorRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.findSuccessorRequest,
                DHT.addr>(
                  this, METHODID_FIND_SUCCESSOR_RPC)))
          .addMethod(
            getIAmPreRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.addr,
                DHT.empty>(
                  this, METHODID_I_AM_PRE_RPC)))
          .addMethod(
            getKeepAliveRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.empty,
                DHT.keepAliveReply>(
                  this, METHODID_KEEP_ALIVE_RPC)))
          .addMethod(
            getGetIdRPCMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                DHT.empty,
                DHT.getIdReply>(
                  this, METHODID_GET_ID_RPC)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class DHTRpcStub extends io.grpc.stub.AbstractStub<DHTRpcStub> {
    private DHTRpcStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DHTRpcStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DHTRpcStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DHTRpcStub(channel, callOptions);
    }

    /**
     * <pre>
     * Find the node's successor
     * </pre>
     */
    public void yourSuccessorRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getYourSuccessorRPCMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * find closest preceding finger of a given id
     * </pre>
     */
    public void closestPrecedingFingerRPC(DHT.closestPrecedingFingerRequest request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getClosestPrecedingFingerRPCMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * find the node's predecessor
     * </pre>
     */
    public void yourPredecessorRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getYourPredecessorRPCMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * find successor of given id
     * </pre>
     */
    public void findSuccessorRPC(DHT.findSuccessorRequest request,
        io.grpc.stub.StreamObserver<DHT.addr> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFindSuccessorRPCMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * notify i am the new predecessor to another node 
     * </pre>
     */
    public void iAmPreRPC(DHT.addr request,
        io.grpc.stub.StreamObserver<DHT.empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIAmPreRPCMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * keep alive message
     * </pre>
     */
    public void keepAliveRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.keepAliveReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getKeepAliveRPCMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * get the node's id
     * </pre>
     */
    public void getIdRPC(DHT.empty request,
        io.grpc.stub.StreamObserver<DHT.getIdReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetIdRPCMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class DHTRpcBlockingStub extends io.grpc.stub.AbstractStub<DHTRpcBlockingStub> {
    private DHTRpcBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DHTRpcBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DHTRpcBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DHTRpcBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Find the node's successor
     * </pre>
     */
    public DHT.addr yourSuccessorRPC(DHT.empty request) {
      return blockingUnaryCall(
          getChannel(), getYourSuccessorRPCMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * find closest preceding finger of a given id
     * </pre>
     */
    public DHT.addr closestPrecedingFingerRPC(DHT.closestPrecedingFingerRequest request) {
      return blockingUnaryCall(
          getChannel(), getClosestPrecedingFingerRPCMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * find the node's predecessor
     * </pre>
     */
    public DHT.addr yourPredecessorRPC(DHT.empty request) {
      return blockingUnaryCall(
          getChannel(), getYourPredecessorRPCMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * find successor of given id
     * </pre>
     */
    public DHT.addr findSuccessorRPC(DHT.findSuccessorRequest request) {
      return blockingUnaryCall(
          getChannel(), getFindSuccessorRPCMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * notify i am the new predecessor to another node 
     * </pre>
     */
    public DHT.empty iAmPreRPC(DHT.addr request) {
      return blockingUnaryCall(
          getChannel(), getIAmPreRPCMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * keep alive message
     * </pre>
     */
    public DHT.keepAliveReply keepAliveRPC(DHT.empty request) {
      return blockingUnaryCall(
          getChannel(), getKeepAliveRPCMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * get the node's id
     * </pre>
     */
    public DHT.getIdReply getIdRPC(DHT.empty request) {
      return blockingUnaryCall(
          getChannel(), getGetIdRPCMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class DHTRpcFutureStub extends io.grpc.stub.AbstractStub<DHTRpcFutureStub> {
    private DHTRpcFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DHTRpcFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DHTRpcFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DHTRpcFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Find the node's successor
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.addr> yourSuccessorRPC(
        DHT.empty request) {
      return futureUnaryCall(
          getChannel().newCall(getYourSuccessorRPCMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * find closest preceding finger of a given id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.addr> closestPrecedingFingerRPC(
        DHT.closestPrecedingFingerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getClosestPrecedingFingerRPCMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * find the node's predecessor
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.addr> yourPredecessorRPC(
        DHT.empty request) {
      return futureUnaryCall(
          getChannel().newCall(getYourPredecessorRPCMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * find successor of given id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.addr> findSuccessorRPC(
        DHT.findSuccessorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getFindSuccessorRPCMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * notify i am the new predecessor to another node 
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.empty> iAmPreRPC(
        DHT.addr request) {
      return futureUnaryCall(
          getChannel().newCall(getIAmPreRPCMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * keep alive message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.keepAliveReply> keepAliveRPC(
        DHT.empty request) {
      return futureUnaryCall(
          getChannel().newCall(getKeepAliveRPCMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * get the node's id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<DHT.getIdReply> getIdRPC(
        DHT.empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetIdRPCMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_YOUR_SUCCESSOR_RPC = 0;
  private static final int METHODID_CLOSEST_PRECEDING_FINGER_RPC = 1;
  private static final int METHODID_YOUR_PREDECESSOR_RPC = 2;
  private static final int METHODID_FIND_SUCCESSOR_RPC = 3;
  private static final int METHODID_I_AM_PRE_RPC = 4;
  private static final int METHODID_KEEP_ALIVE_RPC = 5;
  private static final int METHODID_GET_ID_RPC = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DHTRpcImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DHTRpcImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_YOUR_SUCCESSOR_RPC:
          serviceImpl.yourSuccessorRPC((DHT.empty) request,
              (io.grpc.stub.StreamObserver<DHT.addr>) responseObserver);
          break;
        case METHODID_CLOSEST_PRECEDING_FINGER_RPC:
          serviceImpl.closestPrecedingFingerRPC((DHT.closestPrecedingFingerRequest) request,
              (io.grpc.stub.StreamObserver<DHT.addr>) responseObserver);
          break;
        case METHODID_YOUR_PREDECESSOR_RPC:
          serviceImpl.yourPredecessorRPC((DHT.empty) request,
              (io.grpc.stub.StreamObserver<DHT.addr>) responseObserver);
          break;
        case METHODID_FIND_SUCCESSOR_RPC:
          serviceImpl.findSuccessorRPC((DHT.findSuccessorRequest) request,
              (io.grpc.stub.StreamObserver<DHT.addr>) responseObserver);
          break;
        case METHODID_I_AM_PRE_RPC:
          serviceImpl.iAmPreRPC((DHT.addr) request,
              (io.grpc.stub.StreamObserver<DHT.empty>) responseObserver);
          break;
        case METHODID_KEEP_ALIVE_RPC:
          serviceImpl.keepAliveRPC((DHT.empty) request,
              (io.grpc.stub.StreamObserver<DHT.keepAliveReply>) responseObserver);
          break;
        case METHODID_GET_ID_RPC:
          serviceImpl.getIdRPC((DHT.empty) request,
              (io.grpc.stub.StreamObserver<DHT.getIdReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DHTRpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DHTRpcBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return DHT.DHTRpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DHTRpc");
    }
  }

  private static final class DHTRpcFileDescriptorSupplier
      extends DHTRpcBaseDescriptorSupplier {
    DHTRpcFileDescriptorSupplier() {}
  }

  private static final class DHTRpcMethodDescriptorSupplier
      extends DHTRpcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DHTRpcMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DHTRpcGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DHTRpcFileDescriptorSupplier())
              .addMethod(getYourSuccessorRPCMethod())
              .addMethod(getClosestPrecedingFingerRPCMethod())
              .addMethod(getYourPredecessorRPCMethod())
              .addMethod(getFindSuccessorRPCMethod())
              .addMethod(getIAmPreRPCMethod())
              .addMethod(getKeepAliveRPCMethod())
              .addMethod(getGetIdRPCMethod())
              .build();
        }
      }
    }
    return result;
  }
}
