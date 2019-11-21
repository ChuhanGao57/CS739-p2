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

    public static void main(String[] args) {
        System.out.println("fuck you!");
    }
}