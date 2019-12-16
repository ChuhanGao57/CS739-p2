package DHT;

import java.io.IOException;
import java.net.*;
import java.util.*;


public class FailureTest {

    private static Helper m_helper;

    public static InetSocketAddress queryId(long id, InetSocketAddress addr) {
        RPCClient client = new RPCClient(addr);
        InetSocketAddress nodeAddr = client.findSuccessor(id);
        client.shutdown();
        return nodeAddr;
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

    private static void failNodesByAddr(List<InetSocketAddress> addrList, int k) {
        int n = addrList.size();
        if(k == 0) {
            System.out.println("Err: No failure");
            return;
        }
        if(k >= n) {
            System.out.println("Err: trying to fail too many nodes");
            return;
        }
        int i;
        int reservoir[] = new int[k]; 
        for (i = 0; i < k; i++) 
            reservoir[i] = i; 
          
        Random r = new Random(0); 
          
        // Iterate from the (k+1)th element to nth element 
        for (; i < n; i++) 
        { 
            // Pick a random index from 0 to i. 
            int j = r.nextInt(i + 1); 
            if(j < k) 
                reservoir[j] = i;             
        } 
        Arrays.sort(reservoir);
        boolean success = true;
        for(i = k- 1; i >= 0; i--) {
            success = success && killNode(addrList.get(reservoir[i]));
            addrList.remove(reservoir[i]);
        }
        if(!success)
            System.out.println("Failing nodes unsuccessful");
    }

    private static TreeMap<Long, InetSocketAddress> buildTreeMapByAddr(List<InetSocketAddress> addrList, long[] idList) {
        TreeMap<Long, InetSocketAddress> tree = new TreeMap<>();
        for(int i = 0; i < addrList.size(); i++) {
            tree.put(idList[i], addrList.get(i));
        }
        return tree;
    }

    private static double queryAccByAddr(List<InetSocketAddress> addrList, int numKey, TreeMap<Long, InetSocketAddress> tree) {
        int numNode = addrList.size();
        int[] errCnt = new int[numNode];
        int totalCnt = numKey * numNode;
        for(int i = 0; i < numKey; i++) {
            String randomKey = randomString();
            InetSocketAddress nodeCorrect = correctQueryByAddr(randomKey, tree);
            for(int j = 0; j < numNode; j++) {
                //DHTNode node = nodeList.get(j);
                InetSocketAddress query = queryId(Helper.hashString(randomKey), addrList.get(j));
                if(query.getPort() != nodeCorrect.getPort()) {
                    errCnt[j] += 1;
                    System.out.println(query.toString() + " VS " + nodeCorrect.toString());
                    //debugInfo(randomKey, nodeList);
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

    private static InetSocketAddress correctQueryByAddr(String key, TreeMap<Long, InetSocketAddress> tree) {
        // int numNode = tree.size();
        long hash = Helper.hashString(key);
        Long succId = tree.ceilingKey(hash);
        if(succId == null) 
            return tree.firstEntry().getValue();
        else
            return tree.get(succId);
    }

    public static void testFailureWithKill(int numNode, int numFail) {
        m_helper = new Helper();
        int numKey = 200;
        //int numFail = 1;
        int timeToSleep = (int) (0.5 * 1000); // in ms
        int initialSleep = 10 * 1000;
        List<InetSocketAddress> addrList = new ArrayList<>();
        int startingPort = 8001;
        for(int i = 0; i < numNode; i++) {
            try {
                addrList.add(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), startingPort + i));
                //addrList.add(Helper.createSocketAddress("127.0.0.1", port))
            } catch(UnknownHostException ex) {
                System.out.println("Cannot find host");
                return;
            }
        }
            
        //addrList.sort((InetSocketAddress a1, InetSocketAddress a2) -> Long.compare(Helper.hashSocketAddress(a1), Helper.hashSocketAddress(a2)));
        
        try {
        
            System.out.println("Sleeping " + initialSleep/1000 + " before testing");
            try {
                Thread.sleep(initialSleep);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Start testing");

            System.out.println("Failing " + numFail + " nodes");
            failNodesByAddr(addrList, numFail);
            numNode = addrList.size();

            long[] idList = new long[numNode];
            for(int i = 0; i < numNode; i++) {
                RPCClient client = new RPCClient(addrList.get(i));
                idList[i] = -1;
                for(int retry = 0; retry < 3; retry++) {
                    long id = client.getId();
                    if(id != -1) {
                        idList[i] = id;
                        break;
                    }
                    else {
                        try {
                            Thread.sleep(100);
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        
                    }
                }
                client.shutdown();
                if(idList[i] == -1) {
                    System.out.println("Cannot reach node " + i);
                    return;
                }
            }


            for(long id : idList) {
                System.out.println("Node id: " + id);
            }

            TreeMap<Long, InetSocketAddress> tree = buildTreeMapByAddr(addrList, idList);

            long testStart = System.currentTimeMillis();
            while(true) {
                long lastTestTime = System.currentTimeMillis();
                //double accuracy = queryAccByAddr(addrList, numKey / addrList.size(), idList);
                double accuracy = queryAccByAddr(addrList, numKey / addrList.size(), tree);
                System.out.println("Time: " + (double)(lastTestTime - testStart) / 1000 + "sec, Accuracy: " + accuracy + ", average query latency: " + (double)(System.currentTimeMillis() - lastTestTime) / numKey/addrList.size() + "ms");
                long currTime = System.currentTimeMillis();
                if(currTime - testStart > 30 * 1000)
                    break;
                try {
                    if(timeToSleep - (currTime - lastTestTime) > 0)
                        Thread.sleep(timeToSleep - (currTime - lastTestTime));
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            

        } finally {
            for(int i = 0; i < numNode; i++) {
                killNode(addrList.get(i));
            }
        }
        
    }

    

    private static boolean killNode(InetSocketAddress nodeAddr) {
        RPCClient client = new RPCClient(nodeAddr);
        boolean res = client.killNode();
        client.shutdown();
        return res;
    }
}