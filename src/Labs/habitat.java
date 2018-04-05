package Labs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class habitat {
	public static int drone_life = 5000;
	public static int worker_life = 5000;
	public static int n1=1,n2=2;
	public static double p = 0.9;
	public static MyFrame window = new MyFrame("����������� ������ �� ���������� ����������������");
	private static long start_time = System.currentTimeMillis(), prev_time, stop_time=0;
	private static ConcreateFactory factory = new ConcreateFactory(window);
	public static double k = 0.2;
	public static Image img_drone, img_worker;
	public static int num_of_drones = 0, num_of_workers=0;
	private static long dn1 = 0, dn2 = 0;
	public static boolean status = false;
	public Clone copy_list;
	private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    static long thisTime;
	long T_live1;
	long T_live2;
	
	public habitat(){
		try{
			img_worker = ImageIO.read(new File("worker_bee.png"));
			img_drone = ImageIO.read(new File("drone_bee.png"));
		}
		catch(IOException e){};
	}
	public static void Update(long time_from_begin){
		synchronized (Singleton.getList()){
			
			if(status){
				long current_time = time_from_begin - start_time-stop_time;
		        long delta_time = current_time - prev_time;
		        dn1 += delta_time; dn2 += delta_time;
		        
		        if (dn1/1000 >= n1){
		            dn1 = 0;
		            if(factory.CreateTrialDrone()!=null) num_of_drones++;
		            
		        }
		        if (dn2/1000 >= n2){
		            dn2 = 0;
		            if(factory.CreateTrialWorker()!=null) num_of_workers++;
		        }        
				prev_time = current_time;
				window.setTimeText(current_time);
				for(int i=0; i<Singleton.getList().size(); i++){
					if(Singleton.getList().get(i).life_time+Singleton.getList().get(i).birth_time <= System.currentTimeMillis()){
						Singleton.getHashSet().remove(Singleton.getList().get(i).GetId());
						Singleton.getTreeMap().remove(Singleton.getList().get(i).GetId());
						Singleton.getList().get(i).die();
						Singleton.getList().remove(i);
						//window.repaint();
					}
					
				}
			}
			else{
				stop_time=time_from_begin-prev_time-start_time;
			}
		}
	}
	
	public static void newHabitat(){
		start_time = System.currentTimeMillis();
		stop_time=0;
		num_of_drones = 0; 
		num_of_workers=0;
		prev_time=0;
		dn1 = 0; 
		dn2 = 0;
		Singleton.getList().clear();
	}
	
	public Object requestTransport() {
        synchronized (Singleton.getList()){
	        copy_list.objects=Singleton.getList();
	        copy_list.properties=Singleton.getTreeMap();
	        copy_list.set=Singleton.getHashSet();
	        return copy_list;
        }
    }

    public void acquiredTransport(Object ants) {
        synchronized (Singleton.getList()){
        Clone request = (Clone)ants;
        ArrayList<Bee> temp = Singleton.getList();
        TreeMap<Integer,Long> temp1 = Singleton.getTreeMap();
        HashSet<Integer> temp2 = Singleton.getHashSet();
        temp=request.objects;
        temp1=request.properties;
        temp2=request.set;
        
        /*for(Map.Entry<Integer, Long> item : list.properties.entrySet())
            item.setValue((long)0);
        }*/
        }
    }
	
	public static boolean getStatus(){
		return status;
	}
	
	
	
	
	
	
	
	/********************************************/
    public static Connection initSQL(){
        final String url = "jdbc:mysql://localhost:3306/mydbtest";
        final String user = "root";
        final String password = "";



        try {
            return con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class SaveSQL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveSQL();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class LoadSQL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("SQL подключена");
            try {
                loadSQL();
            } catch (SQLException e1) { e1.printStackTrace(); }
        }
    }

    public static void saveSQL() throws SQLException {
        try  {
            con=initSQL();
            stmt = con.createStatement();
        }catch (Exception e){e.printStackTrace();}
        try {
            stmt.executeUpdate("DELETE FROM mydbtest.bees");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i =0;i<Singleton.getList().size();i++){
            Bee tr = (Bee) Singleton.getList().get(i);
            String name1 = tr.getClass().getName();
            int pX = tr.pos_x;
            int pY = tr.pos_y;
            long tB=1000000;
            for(Map.Entry e : (Singleton.getTreeMap()).entrySet()){
            	if((int)e.getKey()==i) tB = Singleton.getTreeMap().get(i);
        	}
            
            int id = tr.id;
            String query = "INSERT INTO mydbtest.bees (name, posX, posY, timeBorn,id) \n" +
                    " VALUES ('"+name1+"',"+ pX+","+pY+","+tB+","+ id+");";
            try {
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        stmt.close();
        con.close();
    }

    public class SaveAutoSQL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveDroneSQL();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void saveDroneSQL() throws SQLException {
        try  {
            con=initSQL();
            stmt = con.createStatement();
        }catch (Exception e){e.printStackTrace();}
        try {
            stmt.executeUpdate("DELETE FROM mydbtest.bees");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i =0;i<Singleton.getList().size();i++){
            Bee tr = (Bee) Singleton.getList().get(i);
            if (tr instanceof Drone){
                String name1 = tr.getClass().getName();
                int pX = tr.pos_x;
                int pY = tr.pos_y;
                int id = tr.id;
                long tB=1000000;
                for(Map.Entry e : (Singleton.getTreeMap()).entrySet()){
                	if((int)e.getKey()==i) tB = Singleton.getTreeMap().get(i);
            	}
                String query = "INSERT INTO mydbtest.bees (name, posX, posY, timeBorn,id) \n" +
                        " VALUES ('"+name1+"',"+ pX+","+pY+","+tB+","+ id+");";
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        stmt.close();
        con.close();

    }

    public class SaveWorkerSQL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                saveWorkerSQL();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void saveWorkerSQL() throws SQLException {
        try  {
            con=initSQL();
            stmt = con.createStatement();
        }catch (Exception e){e.printStackTrace();}
        try {
            stmt.executeUpdate("DELETE FROM mydbtest.bees");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i =0;i<Singleton.getList().size();i++){
            Bee tr = (Bee) Singleton.getList().get(i);
            if (tr instanceof Worker){
                String name1 = tr.getClass().getName();
                int pX = tr.pos_x;
                int pY = tr.pos_y;
                long tB=1000000;
                for(Map.Entry e : (Singleton.getTreeMap()).entrySet()){
                	if((int)e.getKey()==i) tB = Singleton.getTreeMap().get(i);
            	}
                int id = tr.id;
                String query = "INSERT INTO mydbtest.bees (name, posX, posY, timeBorn,id) \n" +
                        " VALUES ('"+name1+"',"+ pX+","+pY+","+tB+","+ id+");";
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        stmt.close();
        con.close();
    }

    public class LoadDroneSQL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                loadDroneSQL();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void loadDroneSQL() throws SQLException {
        try  {
            con=initSQL();
            stmt = con.createStatement();
        }catch (Exception e){e.printStackTrace();}
        Singleton.getList().clear();
        Singleton.getTreeMap().clear();
        Singleton.getHashSet().clear();
        String query = "select name, posX,posY,timeBorn,id from Bees where name = 'Labs.Drone'";

        rs = stmt.executeQuery(query);

        while (rs.next()) {
            String name = rs.getString(1);
            int x = rs.getInt(2);
            int y = rs.getInt(3);
            long timeBorn = rs.getInt(4);
            int id = rs.getInt(5);


            Class tr = null;//говорим, что класс будет соответсвовать названию класса из файла
            try {
                tr = Class.forName(name);
                Constructor cons = tr.getDeclaredConstructor(int.class, int.class);//поиск конструктора с параметрами int,int
                Bee tr_ = (Bee) cons.newInstance(x, y);//создаем объект класса str  с помощью конструктора cons
                Singleton.getList().add(tr_);
                Singleton.getTreeMap().put(tr_.id,thisTime);
                Singleton.getHashSet().add(tr_.id);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) { e.printStackTrace(); }
            System.out.printf("�������� :"+name+" "+x+" "+y+" "+timeBorn+" "+id+"\n");
        }
        stmt.close();
        con.close();
    }

    public class LoadWorkerSQL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                loadWorkerSQL();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void loadWorkerSQL() throws SQLException {
        try  {
            con=initSQL();
            stmt = con.createStatement();
        }catch (Exception e){e.printStackTrace();}
        Singleton.getList().clear();
        Singleton.getTreeMap().clear();
        Singleton.getHashSet().clear();
        String query = "select name, posX,posY,timeBorn,id from Bees where name = 'Labs.Worker'";

        rs = stmt.executeQuery(query);

        while (rs.next()) {
            String name = rs.getString(1);
            int x = rs.getInt(2);
            int y = rs.getInt(3);
            long timeBorn = rs.getInt(4);
            int id = rs.getInt(5);

            Class tr = null;//говорим, что класс будет соответсвовать названию класса из файла
            try {
                tr = Class.forName(name);
                Constructor cons = tr.getDeclaredConstructor(int.class, int.class);//поиск конструктора с параметрами int,int
                Bee tr_ = (Bee) cons.newInstance(x, y);//создаем объект класса str  с помощью конструктора cons
                Singleton.getList().add(tr_);
                Singleton.getTreeMap().put(tr_.id,thisTime);
                Singleton.getHashSet().add(tr_.id);
            } catch (Exception e) { e.printStackTrace(); }
            System.out.printf("�������� :" + name + " " + x + " " + y  + " " + timeBorn + " " + id + "\n");
        }
        stmt.close();
        con.close();
    }

    public static void loadSQL() throws SQLException {
        try  {
            con=initSQL();
            stmt = con.createStatement();
        }catch (Exception e){e.printStackTrace();}

        // con = DriverManager.getConnection(url, user, password);

        // getting Statement object to execute query
        // stmt = con.createStatement();
        Singleton.getList().clear();
        Singleton.getTreeMap().clear();
        Singleton.getHashSet().clear();
        String query = "select name, posX,posY,timeBorn,id from Bees";

        rs = stmt.executeQuery(query);

        while (rs.next()) {
            String name = rs.getString(1);
            int x = rs.getInt(2);
            int y = rs.getInt(3);
            long timeBorn = rs.getInt(4);
            int id = rs.getInt(5);


            Class tr = null;//говорим, что класс будет соответсвовать названию класса из файла
            try {
                tr = Class.forName(name);
                Constructor cons = tr.getDeclaredConstructor(int.class, int.class);//поиск конструктора с параметрами int,int
                Bee tr_ = (Bee) cons.newInstance(x, y);//создаем объект класса str  с помощью конструктора cons
                Singleton.getList().add(tr_);
                Singleton.getTreeMap().put(tr_.id,thisTime);
                Singleton.getHashSet().add(tr_.id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("�������� :" + name + " " + x + " " + y + " " + timeBorn + " " + id + "\n");
        }
        stmt.close();
        con.close();
    }
/**************************/
	
	
}

