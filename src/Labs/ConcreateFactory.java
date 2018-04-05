package Labs;

import java.util.List;

public class ConcreateFactory implements AbstractFactory {
	MyFrame window;
    
    public ConcreateFactory(MyFrame win){
        this.window = win;
    }
    
	public Drone CreateTrialDrone(){
		if (Singleton.getList().size()!=0){
			if (habitat.num_of_drones < Singleton.getList().size()* habitat.k){
				Drone insect = new Drone((int)((MyFrame.getW()-habitat.img_drone.getWidth(null))*Math.random()),(int)((MyFrame.getH()-habitat.img_drone.getHeight(null))*Math.random()));
				Singleton.getList().add(insect);
				int id = (int)(Math.random()*100);
				while(!Singleton.getHashSet().add(id)) id = (int)(Math.random()*100);
				insect.setId(id);
				
				Singleton.getTreeMap().put(id, insect.birth_time);
				window.getSpace().drawImage(habitat.img_drone, insect.pos_x, insect.pos_y, null);
				return insect;
			}
		}	
		return null;
	}
	
	public Worker CreateTrialWorker(){
		if (habitat.p > Math.random()){
			Worker insect = new Worker((int)((MyFrame.getW()-habitat.img_worker.getWidth(null))*Math.random()),(int)((MyFrame.getH()-habitat.img_worker.getHeight(null))*Math.random()));
			Singleton.getList().add(insect);
			
			int id = (int)(Math.random()*100);
			while(!Singleton.getHashSet().add(id)) id = (int)(Math.random()*100);
			insect.setId(id);
			
			Singleton.getTreeMap().put(id, insect.birth_time);
			window.getSpace().drawImage(habitat.img_worker, insect.pos_x, insect.pos_y, null);
			return insect;
		}	
		return null;
	}
}
