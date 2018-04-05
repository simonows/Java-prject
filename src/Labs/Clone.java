
package Labs;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;



public class Clone implements Serializable{
    public ArrayList<Bee> objects;
    public TreeMap<Integer,Long> properties;
    public HashSet<Integer> set;
    
    public Clone(){
        objects= new ArrayList<Bee>();
        properties = new TreeMap<Integer,Long>();
        set=new HashSet<Integer>();
    }
    
    private static Clone instance;
    
    public static synchronized  Clone getInstance() {
    if (instance == null) {
      instance = new Clone();
    }
    return instance;
  }
    
  
}