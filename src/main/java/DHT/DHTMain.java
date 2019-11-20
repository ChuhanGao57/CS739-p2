package DHT;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DHTMain {
    private static DHTServer server;

    public static void main(String[] args) {
        // TODO: implement
        server = new DHTServer("localhost", 8888);
        try {
            server.start();
            System.out.println(server.findSuccessor(123456).toString());
        } catch (IOException e) {
            //TODO: handle exception
        }
    } 
}