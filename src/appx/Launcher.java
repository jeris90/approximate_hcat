package appx;

import java.util.LinkedList;

import appx.af.ArgumentationFramework;
import appx.parser.AFParser;

public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArgumentationFramework af = AFParser.readingCNF("/Users/jdelobelle/Documents/recherche/approximation_semantique/instances/BA-numArg10-pbCycle0-0.cnf");
		
		/*af.add_attack(0, 1);
		af.add_attack(1, 0);
		af.add_attack(1, 2);
		*/
		System.out.println(af);
	}

}
