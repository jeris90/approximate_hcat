package appx;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.LinkedList;

import appx.af.ArgumentationFramework;
import appx.extensionsemantics.approximate.CategorizedBasedApproximateSolver;
import appx.extensionsemantics.exact.SimpleGroundedSemanticsSolver;
import appx.gradualsemantics.Categorizer;
import appx.parser.AFParser;
import appx.parser.ParsingException;
import appx.solver.Solution;
import appx.solver.Solver;
import appx.task.Problem;
import appx.task.Semantics;
import appx.task.Task;

public class Launcher {

	/***
	 * Provides author(s) and version information of the solver.
	 */
	private static void information() {
		System.out.println("ARIPOTER_hcat v1.0");
		System.out.println("Jérôme Delobelle, jerome.delobelle@u-paris.fr");
		System.out.println("Jean-Guy Mailly, jean-guy.mailly@u-paris.fr");
		System.out.println("Julien Rossit, julien.rossit@u-paris.fr");
	}
	
	/***
	 * Provides the user with the different options available.
	 * 
	 * @param options
	 */
	private static void help(Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jarfile", options, true);
	}
	
	/***
	 * Create an Options object containing the different available options
	 * (short/long option, description, arguments, mandatory or not, etc).
	 * 
	 * @return options
	 */
	private static Options configParameters() {

		Option profileDirectoryOption = Option.builder("f").longOpt("input_AF") //
				.desc("Path of the file containing the AF.").hasArg(true).argName("input_AF").required(false).build();

		Option taskOption = Option.builder("p").longOpt("task") //
				.desc("<task> is a computational problem supported by the solver (e.g. DC-CO, DS-PR).").hasArg(true)
				.argName("task").required(false).build();

		Option problemOption = Option.builder("problems").longOpt("problems") //
				.desc("Prints the supported computational problems and exits").hasArg(false).argName("task")
				.required(false).build();

		Option argumentOption = Option.builder("a").longOpt("argument")
				.desc("Quary argument for credulous and skeptical acceptance").hasArg(true).argName("argument")
				.required(false).build();

		Options options = new Options();

		options.addOption(profileDirectoryOption);
		options.addOption(taskOption);
		options.addOption(problemOption);
		options.addOption(argumentOption);

		// Add option for argument query

		return options;
	}
	
	/***
	 * A method of retrieving the options given by the user and checking whether the
	 * options entered are valid or not.
	 * 
	 * @param args
	 * @return Options
	 */
	private static Options commandLineManagement(String[] args) {

		Options options = configParameters();

		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;

		try {
			line = parser.parse(options, args);
			
			if (line.hasOption("f")) {
				//System.out.println("Input AF");
			}

			if (line.hasOption("p")) {
				//System.out.println("Task");
			}

			if (line.hasOption("problems")) {
				//System.out.println("Problem");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Error parsing command-line arguments!\n");
			help(options);
			System.exit(1);
		}
		return options;
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			information();
		}
		else {
			Options options = commandLineManagement(args);

			CommandLineParser parser = new DefaultParser();
			CommandLine line = null;

			String afFile = null;
			String problem = null;
			String semantics = null;
			//double threshold = -1;
			double threshold = 0.5;
			String argumentName = null;
			
			try {
				line = parser.parse(options, args);

				if (line.hasOption("f")) {
					afFile = line.getOptionValue("f");
					// TO DO: handle the file format, and provide several parsers.
				}

				if (line.hasOption("p")) {
					String task = line.getOptionValue("p");
					if (task.contains("-")) {
						problem = task.split("-")[0];
						semantics = task.split("-")[1];
					} else {
						System.err.println("Error parsing command-line arguments!\n");
						help(options);
						System.exit(1);
					}
					if (!problem.equals("DC") && !problem.equals("DS")) {
						System.err.println("This software only supports problems DC and DS.\n");
						help(options);
						System.exit(1);
					}
					if (line.hasOption("a")) {
						argumentName = line.getOptionValue("a");
					} else {
						System.err.println("Missing argument name.");
						help(options);
						System.exit(1);
					}
				}

				if (line.hasOption("problems")) {
					System.out.println("Problem");
				}

			} catch (ParseException e) {
				System.err.println("Error parsing command-line arguments!\n");
				help(options);
				System.exit(1);
			}
			
			if (afFile == null) {
				System.err.println("Missing filename.");
				help(options);
				System.exit(1);
			}
			
			ArgumentationFramework af;
			Solver solver = null;
			
			long temps_start = System.currentTimeMillis();
			af = AFParser.readingCNF(afFile);
			long temps_end = System.currentTimeMillis();
			//System.out.println("Import time : " + (temps_end-temps_start)/1000. + " secondes");
			System.out.print((temps_end - temps_start)/1000.+";");
				
			solver = new CategorizedBasedApproximateSolver(threshold);
				
			Task task = new Task(Problem.valueOf(problem), Semantics.valueOf(semantics));
			task.addOption("-a", argumentName);
			System.out.print(solver.solve(task, af));
		}
	}

}
