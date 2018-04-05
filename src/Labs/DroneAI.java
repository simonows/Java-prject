package Labs;


public class DroneAI extends BaseAI {

	@Override
	public void moution() {
		synchronized (Singleton.getList()) {
            for (Bee t : Singleton.getList())
                if (t instanceof Drone)
                    t.move();
        }
	}

}
