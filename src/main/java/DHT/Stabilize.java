package DHT;

public class Stabilize extends Thread {
    private DHTServer server;
    private boolean alive;

    public Stabilize(DHTServer server) {
      this.server = server;
      alive = true;
    }
    
    @Override
    public void run() {
        // implement here
    }
}