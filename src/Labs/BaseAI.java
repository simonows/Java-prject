package Labs;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseAI extends Thread{
	protected boolean status_stop = false;
    protected boolean status_pause = true; // в режиме ожидания
	
	public abstract void moution();
	
	@Override
	public void run(){
		while(!status_stop){
			if (status_pause) 
				synchronized (this) {
		            try {
		                this.wait();
		            } catch (InterruptedException ex) {
		                Logger.getLogger(BaseAI.class.getName()).log(Level.SEVERE, null, ex);
		            }
	            }
			else
				try { 
	            	sleep(5); 
	            } catch (InterruptedException e) { 
	            	e.printStackTrace(); 
	            }
	            moution(); 
	            habitat.window.repaint();
		}
	}
	
	public void pause() {
        if(!habitat.getStatus()) return;
        status_pause=true;
    }
    
    public void continuE() {
    	if(!habitat.getStatus()) return;
    	status_pause=false;
        synchronized (this){
        	this.notify();
        }  
    }
    
    public void setThreadPriority(int priority) {
        this.setPriority(priority);
    }
    
    public boolean getStatus(){
    	return status_pause;
    }
}
