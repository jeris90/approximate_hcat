package appx.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import appx.af.ArgumentationFramework;

public class AFParser {

	
	public static ArgumentationFramework readingFile(String afFile, String format) {
		ArgumentationFramework af = null;
		
		if(format.equals("apx")) {
			af = readingAPX(afFile);
		}
		
		if(format.equals("cnf")) {
			af = readingCNF(afFile);
		}
		
		if(af == null) {
			System.out.println("Error : unknown format for the input file (apx or cnf).");
			System.exit(0);
		}
		
		return af;
	}
	
	public static ArgumentationFramework readingCNF(String afFile) {
		
		ArgumentationFramework af = null;
		
		try (BufferedReader bf = new BufferedReader(new FileReader(afFile))) {
			String line;
			
			int nb_arg = Integer.parseInt(bf.readLine().split(" ")[2]);
			
			af = new ArgumentationFramework(nb_arg);
			
			for(int index=0;index<nb_arg;index++) {
				af.add_argument(index+1+"", index);
			}
			
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
	
	
public static ArgumentationFramework readingAPX(String afFile) {//throws ParsingException {
		
		int index_arg = 0;
		
		ArgumentationFramework af = null;
		
		try (BufferedReader bf = new BufferedReader(new FileReader(afFile))) {
			
			int nb_arg = find_number_arguments(afFile);
			
			af = new ArgumentationFramework(nb_arg);
			
			String line;
			while ((line = bf.readLine()) != null) {
				if (!line.startsWith("#") && (! line.trim().equals(""))) {
					if (line.startsWith("arg")) {
						af.add_argument(parseAPXArgumentLine(line),index_arg);
						index_arg++;
					} else if (line.startsWith("att")) {
						String[] arguments = parseAPXAttackLine(line);
						int from = af.getArgumentFromName(arguments[0]);
						int to = af.getArgumentFromName(arguments[1]);
						af.add_attack(from, to);
					} else {
						//throw new ParsingException("Incorrect line in file " + afFile + ": " + line);
					}
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
	private static String[] parseAPXAttackLine(String line) {
		return line.substring(4, line.length() - 2).split(",");
	}

	/**
	 * Provides the name of the argument from an APX line
	 * 
	 * @param line the APX line
	 * @return the name of the argument in the line
	 */
	private static String parseAPXArgumentLine(String line) {
		return line.substring(4, line.length() - 2);
	}	

	private static int find_number_arguments(String afFile) {
		int nb_arg = 0;
		try (BufferedReader bf = new BufferedReader(new FileReader(afFile))) {
			String line = bf.readLine();
			while (line.startsWith("arg")) {
				nb_arg++;
				line = bf.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return nb_arg;
		
	}
	
	
}
