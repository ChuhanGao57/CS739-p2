package DHT;

import java.net.InetSocketAddress;

public class AskPredecessor extends Thread {
	
	private DHTNode local;
	private boolean alive;
	
	public AskPredecessor(DHTNode _local) {
		local = _local;
		alive = true;
	}
	
	@Override
	public void run() {
		while (alive) {
			InetSocketAddress predecessor = local.getPredecessor();
			if (predecessor != null) {
				// String response = Helper.sendRequest(predecessor, "KEEP");
				// if (response == null || !response.equals("ALIVE")) {
				// 	local.clearPredecessor();	
                // }
                RPCClient client = new RPCClient(local, predecessor.getHostName(), predecessor.getPort());
				boolean response = client.keepAlive();
				client.shutdown();
                if(!response)
                    local.clearPredecessor();	

			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void toDie() {
		alive = false;
	}
}


