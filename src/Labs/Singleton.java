package Labs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class Singleton {
	private static ArrayList<Bee> insects = new ArrayList<Bee>();
	private static TreeMap<Integer,Long> insects_map = new TreeMap<Integer,Long>();
	private static HashSet<Integer> insects_hash = new HashSet<Integer>();
	
	public static  ArrayList<Bee> getList(){
		return insects;
	}
	
	public static  TreeMap<Integer,Long>  getTreeMap(){
		return insects_map;
	}
	
	public static  HashSet<Integer> getHashSet(){
		return insects_hash;
	}
	
	public static void clear(){
		insects.clear();
		insects_map.clear();
		insects_hash.clear();
	}
}
