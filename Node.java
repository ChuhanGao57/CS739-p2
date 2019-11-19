import java.net.InetSocketAddress;
import java.util.*;
/**
 * Implementation of a Chord Node
 */

public class Node {
    private static final int M = 32; // number of finger table entries
    private long nodeID;
    private InetSocketAddress nodeAddr, predecessor;
    private ArrayList<InetSocketAddress> finger;
    
    // private Listener listener;
    
    
    /**
	 * Constructor
	 * @param _nodeAddr: this node's local address
	 */
	public Node (InetSocketAddress _nodeAddr) {
        nodeAddr = _nodeAddr;
        predecessor = null;
        nodeID = HashLib.hashAddr(nodeAddr);
        finger = new ArrayList<>();
        for(int i = 0; i <= M; i++) {
            finger.add(null);
        }
        // listener = new Listener(this);

    }

    public boolean join(InetSocketAddress addr) {
        if(addr != null) {
            successor = CommLib.requestAddr(addr, "FINDSUCC_" + nodeID);
            if(successor == null) {
                System.out.println(addr.toString() + " cannot be found");
                return false;
            }
            updateFinger(1, successor);
        }
        // listener.start()
        return true;
    }

    public InetSocketAddress findSuccessor(long id) {
        InetSocketAddress succ = getSuccessor();
        InetSocketAddress pred = findPredecessor(id);
        InetSocketAddress ans = null;
        if(!pred.equals(nodeAddr))
            ans = CommLib.requestAddr(pred, "YOURSUCC");
        else 
            ans = succ;
        if(ans == null)
            ans = nodeAddr;
        return ans;
    }

    private void updateFinger(int index, InetSocketAddress addr) {
        finger.set(index, addr);
        if(i == 1 && addr != null)
            if(!addr.equals(nodeAddr))
                notifySuccessor(addr); 
                // TODO: implement notify in CommLib instead of in Node.java
    }

    public InetSocketAddress getSuccessor() {
		if (finger != null && finger.size() > 0) {
			return finger.get(1);
		}
		return null;
	}

    public String notify(InetSocketAddress succAddr) {
		if (succAddr!=null && !succAddr.equals(localAddress))
			return CommLib.sendRequest(succAddr, "IAMPRE_"+nodeAddr.getAddress().toString()+":"+nodeAddr.getPort());
		else
			return null;
	}

    
}