package Labs;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Worker extends Bee implements IBehaviour {
	
	private int cx,cy;

	
	public Worker(int x, int y) {
		cx =x;
		cy = y;
		xp = x;
		yp = y;
		birth_time = System.currentTimeMillis();
		life_time = habitat.worker_life;
		id = (int)(Math.random()*100);
		//dy = (Math.random()*3)+2;
		dy = 3;
		dx= 1;
		//dx = (dy*cx)/cy;
    }
	
	public Image getImage(){
		return habitat.img_worker;
	}
	
	public void MoveTo(int x, int y) {
    }
	public void die(){
		habitat.num_of_workers--;
	}
	
	public void setId(int i){
		id = i;
	}

	@Override
	public void move(){
		if(pos_x>=cx || pos_x<=0) dx*=-1;
		if(pos_y>=cy || pos_y<=0) dy*=-1;
		xp += dx;
		yp += dy;
		pos_x=(int)xp;
		pos_y=(int)yp;
	}
}
