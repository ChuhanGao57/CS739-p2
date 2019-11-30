package DHT;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;


public class DHTBench {
    private static final Logger logger = Logger.getLogger(DHTBench.class.getName());

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
        int targetStringLength = 32;
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

    public static void testQueryFailure(int numFailure) {
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
            System.out.println("Start testing");
            while(true) {
                lastTestTime = System.currentTimeMillis();
                List<Double> res = queryAccuracy(nodeList, numKey);
                System.out.println((System.currentTimeMillis() - startTime)/1000 + ", " + res.get(0) + ", " + res.get(1)/numKey);
                // System.out.println("Time: " + (lastTestTime - startTime) / 1000 + "sec, Accuracy: " + accuracy + ", average query latency: " + (double)(System.currentTimeMillis() - lastTestTime) / numKey/numNode + "ms");
                long currTime = System.currentTimeMillis();
                if(currTime - startTime > 15 * 1000 && numFailure > 0) {
                    List<Integer> selected = new ArrayList<Integer>();
                    Random random = new Random();
                    for(int i = 1; i <= numFailure; i++)
                    {
                        selected.add(i);
                    }
                    for(int i = numFailure + 1; i < numNode; i++)
                    {
                        if ((random.nextInt() % i + i) %i < numFailure)
                        {
                            selected.set((random.nextInt() % numFailure + numFailure)%numFailure, i);
                        }
                    }
                    for (int i = 0; i < numFailure; i++)
                    {
                        logger.info("Shutting down server" + selected.get(i));
                        nodeList.get(selected.get(i)).stopAllThreads();
                        InetSocketAddress addr = nodeList.get(selected.get(i)).getAddress();
                        DHTNode node = new DHTNode(addr);
                        if (!node.join(nodeList.get(0).getAddress())) {
                            throw new Exception("restart new node failed!");
                        }
                        nodeList.set(selected.get(i), node);
                    }
                    numFailure = 0;
                }
                if(currTime - startTime > 90 * 1000)
                    break;
                try {
                    if(timeToSleep - (currTime - lastTestTime) > 0)
                        Thread.sleep(timeToSleep - (currTime - lastTestTime));
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            
            
            
            System.out.println("All tests completed");

        } catch(Exception e)  {
            e.printStackTrace();
        } finally {
            for(DHTNode node : nodeList) {
                if(node != null)
                    node.stopAllThreads();
            }
        }

    }

    /*
    @returns: 
    */
    private static List<Double> queryAccuracy(List<DHTNode> nodeList, int numKey) {
        int numNode = nodeList.size();
        TreeMap<Long, DHTNode> tree = buildTreeMap(nodeList);
        int[] errCnt = new int[numNode];
        int totalCnt = numKey *numNode;
        long timeElapsed = 0;
        long startTime;
        for(int i = 0; i < numKey; i++) {
            String randomKey = randomString();
            InetSocketAddress nodeCorrect = correctQuery(randomKey, tree);
            startTime = System.currentTimeMillis();
            for(int j = 0; j < numNode; j++) {
                DHTNode node = nodeList.get(j);
                InetSocketAddress q = queryId(Helper.hashString(randomKey), node.getAddress());
                if(q == null || !q.equals(nodeCorrect)) {
                    errCnt[j] += 1;
                    logger.info("Wrong key, " + q + ", " + nodeCorrect);
                    //System.out.println("Wrong query result");
                    //return;
                }
            }
            timeElapsed += System.currentTimeMillis() - startTime;
        }
        int errCntSum = 0;
        for(int c : errCnt)
            errCntSum += c;
        //System.out.println(errCntSum + " out of " + totalCnt + " queries incorrect");
        List<Double> ret = new ArrayList<Double>();
        ret.add(1 - (double) errCntSum / (double) totalCnt);
        ret.add((double)timeElapsed / 1000);
        return ret;
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

    private static boolean buildRing(int numNode, List<InetSocketAddress> addrList, List<DHTNode> nodeList) {
        int startingPort = 6666;
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
        return true;
    }

    private static TreeMap<Long, DHTNode> buildTreeMap(List<DHTNode> nodeList) {
        TreeMap<Long, DHTNode> tree = new TreeMap<>();
        for(DHTNode node : nodeList) {
            tree.put(node.getId(), node);
        }
        return tree;
    }


    public static void main(String[] args) throws IOException, InterruptedException, UnknownHostException {
        DHTBench.testQueryFailure(Integer.parseInt(args[0]));

    } 
}
