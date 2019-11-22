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
			iAmPre(value);
		}
    }
    
    /**
	 * Notify successor that this node should be its predecessor
	 * @param successor
	 */
	public void iAmPre(InetSocketAddress successor) {
		if (successor!=null && !successor.equals(localAddress)) {
        // if(true) {
            //System.out.println("Creating new RPC Client");
            RPCClient client = new RPCClient(this, successor.getHostName(), successor.getPort());
            client.iAmPre();
        }
			// return Helper.sendRequest(successor, "IAMPRE_"+localAddress.getAddress().toString()+":"+localAddress.getPort());
		
    }

    /**
	 * Being notified by another node, set it as my predecessor if it is.
	 * @param newpre
	 */
	public void notified (InetSocketAddress newpre) {
		if (predecessor == null || predecessor.equals(localAddress)) {
			this.setPredecessor(newpre);
		}
		else {
			long oldpre_id = Helper.hashSocketAddress(predecessor);
			long local_relative_id = Helper.computeRelativeId(localId, oldpre_id);
			long newpre_relative_id = Helper.computeRelativeId(Helper.hashSocketAddress(newpre), oldpre_id);
			if (newpre_relative_id > 0 && newpre_relative_id < local_relative_id)
				this.setPredecessor(newpre);
		}
	}
    
    public static void main (String[] args) throws IOException, InterruptedException {
        
    }

    /**
	 * Set predecessor using a new value.
	 * @param pre
	 */
	private synchronized void setPredecessor(InetSocketAddress pre) {
		predecessor = pre;
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