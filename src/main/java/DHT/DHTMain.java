package DHT;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class DHTMain {

    private static Helper m_helper;

    public static long randomIdInRange(long lo, long hi) {
        // Random random = new Random();
        // long value = random.nextLong();
        //return lo + value % (hi - lo);
        return lo + (long) (Math.random() * (hi - lo));
    }
    
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

    public static InetSocketAddress queryId(long id, InetSocketAddress addr) {
        RPCClient client = new RPCClient(addr);
        InetSocketAddress nodeAddr = client.findSuccessor(id);
        client.shutdown();
        return nodeAddr;
    }

    //private static class idComparator implements Comparator<>()

    public static void testQuery() throws UnknownHostException, IOException{
        m_helper = new Helper();

        int numNode = 3;
        int timeToSleep = 60 * 1000; // in ms
        List<InetSocketAddress> addrList = new ArrayList<>();
        List<DHTNode> nodeList = new ArrayList<>();
        int startingPort = 8001;
        for(int i = 0; i < numNode; i++) {
            addrList.add(new InetSocketAddress("localhost", startingPort + i));
            nodeList.add(new DHTNode(addrList.get(i)));
        }

        
        try {
            boolean successJoin = false;
            for(int i = 0; i < numNode; i++) {
                if(i == 0) {
                    if(!nodeList.get(0).join(addrList.get(0))) {
                        System.out.println("Cannot start ring");
                        return;
                    }
                    
                }
                else {
                    if(!nodeList.get(i).join(addrList.get(i-1))) {
                        System.out.println("Node " + i + " cannot join ring");
                        return;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Ring established");

            nodeList.sort((DHTNode n1, DHTNode n2) -> Long.compare(n1.getId(), n2.getId()));
            addrList.sort((InetSocketAddress a1, InetSocketAddress a2) -> Long.compare(Helper.hashSocketAddress(a1), Helper.hashSocketAddress(a2)));

            for(int i = 0; i < numNode; i++) {
                if(!nodeList.get(i).getAddress().equals(addrList.get(i))) {
                    System.out.println("Address wrong: " + nodeList.get(i).getAddress() + " VS " + addrList.get(i));
                    return;
                }
            }
            System.out.println("Sleeping " + timeToSleep/1000 + " sec before testing");
            try {
                Thread.sleep(timeToSleep);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Start testing");
            int errCnt = 0, totalCnt = 0;
            for(int i = 0; i < numNode; i++) {
                DHTNode node1 = nodeList.get(i);
                DHTNode node2 = nodeList.get((i+1) % numNode);
                long id1 = node1.getId();
                long id2 = node2.getId();
                for(int j = 0; j < 100; j++) {
                    long randomId;
                    if(id1 < id2)
                        randomId = randomIdInRange(id1, id2);
                    else {
                        randomId = randomIdInRange(0, id2);
                    }
                    for(DHTNode node : nodeList) {
                        if(!queryId(randomId, node.getAddress()).equals(node2.getAddress())) {
                            errCnt += 1;
                            //System.out.println("Wrong query result");
                            //return;
                        }
                        totalCnt += 1;
                    }
                }

            }
            System.out.println(errCnt + " out of " + totalCnt + " querries wrong");


            
            //System.out.println("All tests passed");
        }
        finally {
            for(DHTNode node : nodeList) {
                if(node != null)
                    node.stopAllThreads();
            }
        }


    }


    public static void main(String[] args) throws IOException, InterruptedException, UnknownHostException {
        
        DHTMain.testQuery();

    } 
}