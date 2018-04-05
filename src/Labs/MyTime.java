package Labs;

import java.util.*;

public class MyTime extends TimerTask {
	@Override
	public void run(){
		new habitat();
		habitat.Update(System.currentTimeMillis()); 
		habitat.window.repaint();
	}
}
