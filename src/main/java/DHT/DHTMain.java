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

    public static String randomString() {
  
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
     
        return generatedString;
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

        int numNode = 6;
        int timeToSleep = 1 * 1000; // in ms
        List<InetSocketAddress> addrList = new ArrayList<>();
        List<DHTNode> nodeList = new ArrayList<>();
        
        

        
        try {
            
            if(!buildRing(numNode, addrList, nodeList)) {
                System.out.println("Build ring failed!");
                return;
            }
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
                        if(queryId(randomId, node.getAddress()).getPort() != node2.getAddress().getPort()) {
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

    public static void testFailure() {
        m_helper = new Helper();
        int numNode = 3;
        int numKey = 50;
        int timeToSleep = 3 * 1000; // in ms
        List<InetSocketAddress> addrList = new ArrayList<>();
        List<DHTNode> nodeList = new ArrayList<>();
        try {
            if(!buildRing(numNode, addrList, nodeList)) {
                System.out.println("Build ring failed!");
                return;
            }
            long startTime = System.currentTimeMillis();
            long lastTestTime = startTime;
            int initialSleep = 3 * 1000;
            System.out.println("Sleeping " + initialSleep/1000 + " sec before testing");
            try {
                Thread.sleep(initialSleep);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            

            System.out.println("Failing node 1");
            nodeList.get(1).stopAllThreads();
            nodeList.remove(1);

            System.out.println("Start testing");

            while(true) {
                lastTestTime = System.currentTimeMillis();
                double accuracy = queryAccuracy(nodeList, numKey);
                System.out.println("Time: " + (lastTestTime - startTime) / 1000 + "sec, Accuracy: " + accuracy + ", average query latency: " + (double)(System.currentTimeMillis() - lastTestTime) / numKey/numNode + "ms");
                long currTime = System.currentTimeMillis();
                if(currTime - startTime > 30 * 1000)
                    break;
                try {
                    if(timeToSleep - (currTime - lastTestTime) > 0)
                        Thread.sleep(timeToSleep - (currTime - lastTestTime));
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            
            
            
            System.out.println("All tests completed");

        } finally {
            for(DHTNode node : nodeList) {
                if(node != null)
                    node.stopAllThreads();
            }
        }
    }

    public static void testQueryKeys() {
        m_helper = new Helper();

        int numNode = 10;
        int numKey = 50;
        int timeToSleep = 3 * 1000; // in ms
        List<InetSocketAddress> addrList = new ArrayList<>();
        List<DHTNode> nodeList = new ArrayList<>();
        try {
            if(!buildRing(numNode, addrList, nodeList)) {
                System.out.println("Build ring failed!");
                return;
            }
            // for(int i = 0; i < numNode; i++) {
            //     if(!nodeList.get(i).getAddress().equals(addrList.get(i))) {
            //         System.out.println("Address wrong: " + nodeList.get(i).getAddress() + " VS " + addrList.get(i));
            //         return;
            //     }
            // }
            long startTime = System.currentTimeMillis();
            long lastTestTime = startTime;
            // System.out.println("Sleeping " + timeToSleep/1000 + " sec before testing");
            // System.out.println("Start testing");
            while(true) {
                lastTestTime = System.currentTimeMillis();
                double accuracy = queryAccuracy(nodeList, numKey);
                System.out.println("Time: " + (lastTestTime - startTime) / 1000 + "sec, Accuracy: " + accuracy + ", average query latency: " + (double)(System.currentTimeMillis() - lastTestTime) / numKey/numNode + "ms");
                long currTime = System.currentTimeMillis();
                if(currTime - startTime > 30 * 1000)
                    break;
                try {
                    if(timeToSleep - (currTime - lastTestTime) > 0)
                        Thread.sleep(timeToSleep - (currTime - lastTestTime));
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            
            
            
            System.out.println("All tests completed");

        } finally {
            for(DHTNode node : nodeList) {
                if(node != null)
                    node.stopAllThreads();
            }
        }

    }

    private static double queryAccuracy(List<DHTNode> nodeList, int numKey) {
        int numNode = nodeList.size();
        TreeMap<Long, DHTNode> tree = buildTreeMap(nodeList);
        int[] errCnt = new int[numNode];
        int totalCnt = numKey * numNode;
        for(int i = 0; i < numKey; i++) {
            String randomKey = randomString();
            // InetSocketAddress nodeCorrect = correctQuery(randomKey, tree);
            InetSocketAddress nodeCorrect = correctQuery(randomKey, nodeList);
            for(int j = 0; j < numNode; j++) {
                DHTNode node = nodeList.get(j);
                InetSocketAddress query = queryId(Helper.hashString(randomKey), node.getAddress());
                if(query.getPort() != nodeCorrect.getPort()) {
                    errCnt[j] += 1;
                    System.out.println(query.toString() + " VS " + nodeCorrect.toString());
                    debugInfo(randomKey, nodeList);
                    //return;
                }
            }
        }
        int errCntSum = 0;
        for(int c : errCnt)
            errCntSum += c;
        //System.out.println(errCntSum + " out of " + totalCnt + " queries incorrect");
        return 1 - (double) errCntSum / (double) totalCnt;
    }

    private static InetSocketAddress correctQuery(String key, TreeMap<Long, DHTNode> tree) {
        // int numNode = tree.size();
        long hash = Helper.hashString(key);
        // long[] ids = new long[numNode];
        // for(int i = 0; i < numNode; i++) {
        //     ids[i] = nodeList.get(i).getId();
        // }
        // if(hash <= ids[0] || hash > ids[numNode - 1])
        //     return nodeList.get(0).getAddress();
        Long succId = tree.ceilingKey(hash);
        if(succId == null) 
            return tree.firstEntry().getValue().getAddress();
        else
            return tree.get(succId).getAddress();
    }

    private static InetSocketAddress correctQuery(String key, List<DHTNode> nodeList) {
        long hash = Helper.hashString(key);
        int numNode = nodeList.size();
        long[] ids = new long[numNode];
        for(int i = 0; i < numNode; i++) {
            ids[i] = nodeList.get(i).getId();
        }
        if(hash <= ids[0] || hash > ids[numNode - 1])
            return nodeList.get(0).getAddress();
        for(int i = 0; i < numNode - 1; i++) {
            if(hash > ids[i] && hash <= ids[i+1])
                return nodeList.get(i+1).getAddress();
        }
        return null;
    }

    private static void debugInfo(String key, List<DHTNode> nodeList) {
        long hash = Helper.hashString(key);
        System.out.println("Key id: " + Helper.longTo8DigitHex(hash)+" ("+hash*100/Helper.getPowerOfTwo(32)+"%)");
        for(DHTNode node : nodeList) {
            hash = node.getId();
            System.out.println(node.getAddress() + " Node id: " + Helper.longTo8DigitHex(hash)+" ("+hash*100/Helper.getPowerOfTwo(32)+"%)");
        }

        int numNode = nodeList.size();
        long[] ids = new long[numNode];
        for(int i = 0; i < numNode; i++) {
            ids[i] = nodeList.get(i).getId();
        }
        hash = Helper.hashString(key);
        if(hash <= ids[0] || hash > ids[numNode - 1])
            System.out.println("Flag 1");
        for(int i = 0; i < numNode - 1; i++) {
            if(hash > ids[i] && hash <= ids[i+1])
                System.out.println("Flag 2: " + nodeList.get(i+1).getAddress());
        }
    }
 
    private static boolean buildRing(int numNode, List<InetSocketAddress> addrList, List<DHTNode> nodeList) {
        int startingPort = 8001;
        for(int i = 0; i < numNode; i++) {
            addrList.add(new InetSocketAddress("localhost", startingPort + i));
            try {
                nodeList.add(new DHTNode(addrList.get(i)));
            } catch (IOException e) {
				e.printStackTrace();
			}
        }
        boolean successJoin = false;
        for(int i = 0; i < numNode; i++) {
            if(i == 0) {
                if(!nodeList.get(0).join(addrList.get(0))) {
                    System.out.println("Cannot start ring");
                    return false;
                }
                
            }
            else {
                if(!nodeList.get(i).join(addrList.get(i-1))) {
                    System.out.println("Node " + i + " cannot join ring");
                    return false;
                }
            }

            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Ring established");

        nodeList.sort((DHTNode n1, DHTNode n2) -> Long.compare(n1.getId(), n2.getId()));
        addrList.sort((InetSocketAddress a1, InetSocketAddress a2) -> Long.compare(Helper.hashSocketAddress(a1), Helper.hashSocketAddress(a2)));
        for(DHTNode node : nodeList)
            System.out.println("Node id: " + node.getId());
        return true;
    }

    private static TreeMap<Long, DHTNode> buildTreeMap(List<DHTNode> nodeList) {
        TreeMap<Long, DHTNode> tree = new TreeMap<>();
        for(DHTNode node : nodeList) {
            tree.put(node.getId(), node);
        }
        return tree;
    }

    public static void testKill() {
        int numNode = 2;
        List<InetSocketAddress> addrList = new ArrayList<>();
        List<DHTNode> nodeList = new ArrayList<>();
        try {
            int startingPort = 8001;
            for(int i = 0; i < numNode; i++) {
                addrList.add(new InetSocketAddress("localhost", startingPort + i));
                try {
                    nodeList.add(new DHTNode(addrList.get(i)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            DHTNode n1 = nodeList.get(0);
            DHTNode n2 = nodeList.get(1);
            n1.join(n1.getAddress());
            n2.join(n2.getAddress());

            System.out.println("Trying to make RPC call");
            RPCClient client = new RPCClient(n1, n2.getAddress());
            boolean response = client.keepAlive();
            client.shutdown();
            System.out.println(response);

            System.out.println("n2 shutdown");
            n2.stopAllThreads();
            System.out.println("Trying to make RPC call");
            client = new RPCClient(n1, n2.getAddress());
            response = client.keepAlive();
            client.shutdown();
            System.out.println(response);

        } finally {
            for(DHTNode node : nodeList) {
                if(node != null)
                    node.stopAllThreads();
            }
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, UnknownHostException {
        
        DHTMain.testFailure();

    } 
}