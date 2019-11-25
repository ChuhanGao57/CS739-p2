package DHT;

import java.net.InetSocketAddress;
import java.util.Random;

public class FixFingers extends Thread{

	private DHTNode local;
	Random random;
	boolean alive;

	public FixFingers (DHTNode node) {
		local = node;
		alive = true;
		random = new Random();
	}

	@Override
	public void run() {
		while (alive) {
			int i = random.nextInt(31) + 2;
			InetSocketAddress ithfinger = local.find_successor(Helper.ithStart(local.getId(), i));
			local.updateFingers(i, ithfinger);
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