package appx.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import appx.af.ArgumentationFramework;

public class AFParser {

	public static ArgumentationFramework readingCNF(String afFile) {
		
		ArgumentationFramework af = null;
		
		try (BufferedReader bf = new BufferedReader(new FileReader(afFile))) {
			String line;
			
			int nb_arg = Integer.parseInt(bf.readLine().split(" ")[2]);
			
			af = new ArgumentationFramework(nb_arg);
			
			while ((line = bf.readLine()) != null) {
				if (!line.startsWith("#") && (! line.trim().equals(""))) {
					String[] arguments = parseCNFAttackLine(line);
					int attacker = Integer.parseInt(arguments[0]);
					int target = Integer.parseInt(arguments[1]);
					af.add_attack(attacker-1, target-1);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return af;
	}
	
	/**
	 * Proves the name of the arguments involved in an attack from an APX line
	 * 
	 * @param line the APX line
	 * @return the name of the arguments as an array of Strings
	 */
	private static String[] parseCNFAttackLine(String line) {
		return line.split(" ");
	}
	
}
