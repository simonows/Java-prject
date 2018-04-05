package Labs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyFile {
    private static File config, collection;
    
    public MyFile(){
    	config = new File("config.txt");
        collection = new File("collection.txt");
        if(!config.exists())
			try {
				config.createNewFile();
				writeConfig();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        else{
        	readConfig();
        }
    }
    
    public static void writeConfig(){
		try{
            FileWriter fw = new FileWriter(config, false);
            fw.write("T1 ");
            fw.write(habitat.n1+"");
            fw.write("\n");
            
            fw.write("T2 ");
            fw.write(habitat.n2+"");
            fw.write("\n");
            
            fw.write("P ");
            fw.write(habitat.p+"");
            fw.write("\n");
            
            fw.write("t1 ");
            fw.write(habitat.drone_life+"");
            fw.write("\n");
            
            fw.write("t2 ");
            fw.write(habitat.worker_life+"");
            fw.write("\n");
            fw.write("@");
            fw.close();
        } catch (Exception e1){
        	e1.printStackTrace();
        }
		
	}
	
	public static void readConfig(){
		boolean flag = false;
		try {
			FileReader fr = new FileReader(config);
			BufferedReader in = new BufferedReader(fr);
			int c=0;
         	int k=0;
            String []type= new String[5];
            
            type[0] = new String();
            type[1] = new String();
            type[2] = new String();
            type[3] = new String();
            type[4] = new String();
            while((char)(c=fr.read())!='@'){
            	
                if((char)c=='\n') {
                	k++;
                	flag = false;
                }
                else{
                	if(flag) type[k]+=(char)c;
                }
                if((char)c == ' ') flag = true;
            }                  
            habitat.n1 = Integer.parseInt(type[0]);
            habitat.n2 = Integer.parseInt(type[1]);
            habitat.p = Double.parseDouble(type[2]);
            habitat.drone_life = Integer.parseInt(type[3]);
            habitat.worker_life = Integer.parseInt(type[4]);
            fr.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public static void savePopulation() {
        try {
	        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(collection));
	        int len = Singleton.getList().size();
	        oos.writeObject(len);
	        if (Singleton.getList() != null) {                   
	            for (Bee b : Singleton.getList())
	                        oos.writeObject(b);
	        }
	        oos.close();
	        
	        System.out.println("Сохранение объектов");
        } catch (IOException e1) {
        	e1.printStackTrace();
        }             
	}
	
	public static void loadPopulation() {
        Bee bee;
		try  {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(collection));
            int len = (int) in.readObject();
            synchronized(Singleton.getHashSet()){
            	Singleton.getHashSet().clear();
            	Singleton.getTreeMap().clear();
            }
            synchronized(Singleton.getList()){
	            Singleton.getList().clear();
	            habitat.num_of_drones=0;
	            habitat.num_of_workers=0;
	            for (int i = 0; i < len; i++) {
	            	bee = (Bee) in.readObject();
	            	if (bee instanceof Drone) habitat.num_of_drones++;
	            	else habitat.num_of_workers++;
	            	bee.setBirthTime();
	            	Singleton.getList().add(bee);
	            }
            }
            in.close();
            System.out.println("Загрузка объектов");
        } catch (Exception e1) {e1.printStackTrace();}
    }
    
}
