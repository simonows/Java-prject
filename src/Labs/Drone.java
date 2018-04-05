package Labs;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Drone extends Bee implements IBehaviour {
	private long t = System.currentTimeMillis();
	private double temp;
	
	
	public Drone(int x, int y) {
		xp = x;
		yp = y;
		birth_time = System.currentTimeMillis();
		life_time = habitat.drone_life;
		id = (int)(Math.random()*100);
		dx = 1;
		dy = 1;
    }
	
	public Image getImage(){
		return habitat.img_drone;
	}
	
	public void MoveTo(int x, int y) {
    }
	
	public void die(){
		habitat.num_of_drones--;
	}
	
	public void setId(int i){
		id = i;
	}
	
	@Override
	public void move(){
		if(System.currentTimeMillis()-t>1000){
			temp = dx;
			dx = dy;
			dy = temp;
			if(Math.random()>0.5) dx*=-1;
			if(Math.random()>0.5) dy*=-1;
			t = System.currentTimeMillis();
		}
		if(pos_x>=Style.space_width-habitat.img_drone.getWidth(null) || pos_x<=0) dx*=-1;
		if(pos_y>=Style.space_height-habitat.img_drone.getHeight(null) || pos_y<=0) dy*=-1;
		xp += dx;
		yp += dy;
		pos_x=(int)xp;
		pos_y=(int)yp;
	}


}
