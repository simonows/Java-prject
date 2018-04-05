package Labs;

import java.util.*;

public class LabOne {
	public static void main(String args[]){
		Timer my_timer = new Timer();
		MyTime ttt = new MyTime();
		my_timer.schedule(ttt, 0, 1000);
	}
}
