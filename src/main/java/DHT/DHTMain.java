package DHT;

import java.io.IOException;
import java.net.*;

public class DHTMain {
    
    public static void testIAmPre()  throws IOException, InterruptedException {
        InetSocketAddress address1 = new InetSocketAddress("localhost", 9001);
        DHTNode node1 = new DHTNode(address1);
        InetSocketAddress address2 = new InetSocketAddress("localhost", 9002);
        //DHTNode node2 = new DHTNode(address2);
        node1.iAmPre(address2);
        //node2.iAmPre(address1);

        System.out.println("completed");
        //System.out.println(node1.getAddress().getPort() + "'s predecessor is " + node1.getPredecessor().getPort());
        //System.out.println(node2.getAddress().getPort() + "'s predecessor is " + node2.getPredecessor().getPort());
    }

    public static void testGetId() throws IOException{
        InetSocketAddress address1 = new InetSocketAddress("localhost", 9001);
        DHTNode node1 = new DHTNode(address1);
        InetSocketAddress address2 = new InetSocketAddress("localhost", 9002);
        DHTNode node2 = new DHTNode(address2);

        boolean successJoin = false;
        successJoin = node1.join(address1);
        if(!successJoin) {
            System.out.println("Node 1 unable to join ring");
            return;
        }
        successJoin = node2.join(address2);
        if(!successJoin) {
            System.out.println("Node 2 unable to join ring");
            return;
        }

        RPCClient client = new RPCClient(null, address1);
        System.out.println("Node 1 ID: " + client.getId());
        client = new RPCClient(null, address2);
        System.out.println("Node 2 ID: " + client.getId());
        node1.stopAllThreads();
        node2.stopAllThreads();

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        
        DHTMain.testGetId();

    } 
}