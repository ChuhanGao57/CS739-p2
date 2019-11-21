package DHT;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.io.IOException;

public class DHTNode {

	private long localId;
	private InetSocketAddress localAddress;
	private InetSocketAddress predecessor;
    private HashMap<Integer, InetSocketAddress> finger;
    
    // RPCClient rpcClient;
    // RPCServer rpcServer;
    private Listener listener;
	// private Stabilize stabilize;
    // private FixFingers fix_fingers;
    
    public DHTNode (InetSocketAddress address) throws IOException, InterruptedException {

		localAddress = address;
		localId = Helper.hashSocketAddress(localAddress);

		// initialize an empty finge table
		finger = new HashMap<Integer, InetSocketAddress>();
		for (int i = 1; i <= 32; i++) {
			updateIthFinger (i, null);
		}

		// initialize predecessor
        predecessor = null;
        
        

		// initialize threads
        listener = new Listener(this);
        listener.start();
		// stabilize = new Stabilize(this);
		// fix_fingers = new FixFingers(this);
		// ask_predecessor = new AskPredecessor(this);
    }
    


    /**
	 * Update ith finger in finger table using new value
	 * @param i: index
	 * @param value
	 */
	private void updateIthFinger(int i, InetSocketAddress value) {
		finger.put(i, value);

		// if the updated one is successor, notify the new successor
		if (i == 1 && value != null && !value.equals(localAddress)) {
			IAmPre(value);
		}
    }
    
    /**
	 * Notify successor that this node should be its predecessor
	 * @param successor
	 */
	public void IAmPre(InetSocketAddress successor) {
		if (successor!=null && !successor.equals(localAddress)) {
        // if(true) {
            System.out.println("Creating new RPC Client");
            RPCClient client = new RPCClient(successor.getHostName(), successor.getPort());
            client.findSuccessor(111);
        }
			// return Helper.sendRequest(successor, "IAMPRE_"+localAddress.getAddress().toString()+":"+localAddress.getPort());
		
    }
    
    public static void main (String[] args) throws IOException, InterruptedException {
        
    }


    /**
	 * Getters
	 * @return the variable caller wants
	 */

	public long getId() {
		return localId;
	}

	public InetSocketAddress getAddress() {
		return localAddress;
	}

	public InetSocketAddress getPredecessor() {
		return predecessor;
	}

	public InetSocketAddress getSuccessor() {
		if (finger != null && finger.size() > 0) {
			return finger.get(1);
		}
		return null;
	}



}