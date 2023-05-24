package appx.af;

import java.util.HashMap;
import java.util.LinkedList;

public class ArgumentationFramework {
	
	private LinkedList<Integer> [] af_attacker;
	
	private LinkedList<Integer> [] af_attackee;
	
	private HashMap<String,Integer> argument_names;
	
	public ArgumentationFramework(int nb_arg) {
		af_attacker = new LinkedList[nb_arg];
		af_attackee = new LinkedList[nb_arg];
		argument_names = new HashMap<>();
		
		for (int i=0 ; i < nb_arg ; i++) {
			af_attacker[i] = new LinkedList<Integer>(); 
			af_attackee[i] = new LinkedList<Integer>(); 
		}
	}
	
	public void add_attack(int attacker, int target) {
		af_attacker[target].add(attacker);
		af_attackee[attacker].add(target);
	}
	
	public void add_argument(String name_arg, int index_arg) {
		argument_names.put(name_arg, index_arg);
	}
	
	public int getArgumentFromName(String name_arg) {
		return argument_names.get(name_arg);
	}
	
	public LinkedList<Integer>[] get_af_attacker(){
		return af_attacker;
	}
	
	public LinkedList<Integer>[] get_af_attackee(){
		return af_attackee;
	}
	
	public int nb_arguments() {
		return af_attacker.length;
	}
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (int i=0 ; i < af_attacker.length ; i++) {
			res.append(i + " <- " + af_attacker[i].toString() + "\n");
		}
		return res.toString();
	}

}
