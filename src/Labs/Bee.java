package Labs;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.JLabel;

abstract class Bee implements Serializable{
    public int pos_x,pos_y;
    public double dx,dy;
    public Long birth_time;
    public long life_time;
    public int id;
    protected double xp,yp;
    
    public void setBirthTime(){
    	birth_time = System.currentTimeMillis();
    }
    public Image getImage() {
		return null;
	}
    public int getPosX(){
        return pos_x;
    }
    
    
    public int getPosY(){
        return pos_y;
    }
    
    public void die(){
    	
    }
    
    public void setId(int i){
    	
    }
    
    public int GetId(){
    	return id;
    }
    
    public abstract void move();
   
}