package Labs;

public class WorkerAI extends BaseAI{
	@Override
	public void moution(){
		synchronized (Singleton.getList()) {
            for (Bee t : Singleton.getList())
                if (t instanceof Worker)
                    t.move();
        }
	}
}
