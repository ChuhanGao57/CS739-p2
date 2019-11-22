package DHT;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DHTMain {
    
    public void testIAmPre() {
        InetSocketAddress address1 = new InetSocketAddress("localhost", 9001);
        DHTNode node1 = new DHTNode(address1);
        InetSocketAddress address2 = new InetSocketAddress("localhost", 9002);
        DHTNode node2 = new DHTNode(address2);
        node1.iAmPre(address2);
        node2.iAmPre(address1);

        System.out.println();
        System.out.println(node1.getAddress().getPort() + "'s predecessor is " + node1.getPredecessor().getPort());
        System.out.println(node2.getAddress().getPort() + "'s predecessor is " + node2.getPredecessor().getPort());
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        

    } 
}