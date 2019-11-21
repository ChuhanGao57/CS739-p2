package DHT;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DHTMain {
    


    public static void main(String[] args) throws IOException, InterruptedException {
        InetSocketAddress address1 = new InetSocketAddress("localhost", 9001);
        DHTNode node1 = new DHTNode(address1);
        InetSocketAddress address2 = new InetSocketAddress("localhost", 9002);
        DHTNode node2 = new DHTNode(address2);
        node1.IAmPre(address2);
    } 
}