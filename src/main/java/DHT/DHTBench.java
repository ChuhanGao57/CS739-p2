package DHT;

import java.io.IOException;
import java.io.File;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;


public class DHTBench {
    private static final Logger logger = Logger.getLogger(DHTBench.class.getName());

    private static Helper m_helper;

    private static List<InetSocketAddress> addrList;

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

    public static InetSocketAddress queryId(long id, InetSocketAddress addr) {
        RPCClient client = new RPCClient(addr);
        InetSocketAddress nodeAddr = client.findSuccessor(id);
        client.shutdown();
        return nodeAddr;
    }

    public static void testQuery(int sec) {
        m_helper = new Helper();

        int numNode = addrList.size();
        int numKey = 50;
        int timeToSleep = 3 * 1000; // in ms
        try {
            long startTime = System.currentTimeMillis();
            long lastTestTime = startTime;
            logger.info("Start testing");
            while(true) {
                lastTestTime = System.currentTimeMillis();
                List<Double> res = queryAccuracy(addrList, numKey);
                System.out.println((System.currentTimeMillis() - startTime)/1000.0 + ", " + res.get(0) + ", " + res.get(1)/numKey);
                long currTime = System.currentTimeMillis();
                if(currTime - startTime > sec * 1000)
                    break;
                try {
                    if(timeToSleep - (currTime - lastTestTime) > 0)
                        Thread.sleep(timeToSleep - (currTime - lastTestTime));
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            
            
            
            logger.info("All tests completed");

        } catch(Exception e)  {
            e.printStackTrace();
        }
    }

    /*
    @returns: 
    */
    private static List<Double> queryAccuracy(List<InetSocketAddress> addrList, int numKey) {
        int numNode = addrList.size();
        TreeMap<Long, InetSocketAddress> tree = buildTreeMap(addrList);
        int[] errCnt = new int[numNode];
        int totalCnt = numKey *numNode;
        long timeElapsed = 0;
        long startTime;
        for(int i = 0; i < numKey; i++) {
            String randomKey = randomString();
            InetSocketAddress nodeCorrect = correctQuery(randomKey, tree);
            startTime = System.currentTimeMillis();
            for(int j = 0; j < numNode; j++) {
                InetSocketAddress addr = addrList.get(j);
                InetSocketAddress q = queryId(Helper.hashString(randomKey), addr);
                if(q == null || !q.equals(nodeCorrect)) {
                    errCnt[j] += 1;
                    logger.info("Wrong key, " + q + ", " + nodeCorrect);
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

    private static InetSocketAddress correctQuery(String key, TreeMap<Long, InetSocketAddress> tree) {
        long hash = Helper.hashString(key);
        Long succId = tree.ceilingKey(hash);
        if(succId == null) 
            return tree.firstEntry().getValue();
        else
            return tree.get(succId);
    }

    private static TreeMap<Long, InetSocketAddress> buildTreeMap(List<InetSocketAddress> addrList) {
        TreeMap<Long, InetSocketAddress> tree = new TreeMap<>();
        for(InetSocketAddress addr : addrList) {
            tree.put(Helper.hashSocketAddress(addr), addr);
        }
        return tree;
    }


    public static void main(String[] args) throws IOException, InterruptedException, UnknownHostException {
        Scanner sc = new Scanner(new File("/tmp/DHTNode/addr_list"));
        int sec = 60;
        if (args.length > 0) {
            sec = Integer.parseInt(args[0]);
        }
        addrList = new ArrayList<>();
        while (sc.hasNextLine()) {
            String addrs[] = sc.nextLine().split(":");
            addrList.add(new InetSocketAddress(addrs[0], Integer.parseInt(addrs[1])));
        }
        sc.close();
        DHTBench.testQuery(sec);
    } 
}
