package appx.task;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a task to be solved, i.e. a semantics, a problem, and
 * optional additional information.
 * 
 * @author jeanguy
 *
 */
public class Task {

	/**
	 * The semantics
	 */
	private Semantics semantics;

	/**
	 * The problem
	 */
	private Problem problem;

	/**
	 * The optional additional information
	 */
	private Map<String, String> options;

	/**
	 * Constructs a Task from a semantics and a problem.
	 * 
	 * @param s the semantics
	 * @param p the problem
	 */
	public Task(Problem p, Semantics s) {
		semantics = s;
		problem = p;
		options = new HashMap<String, String>();
	}

	/**
	 * Adds an option to the task
	 * 
	 * @param optionName  the option name
	 * @param optionValue the option value
	 */
	public void addOption(String optionName, String optionValue) {
		options.put(optionName, optionValue);
	}

	/**
	 * Provides the semantics corresponding to the current Task
	 * 
	 * @return the semantics
	 */
	public Semantics getSemantics() {
		return semantics;
	}

	/**
	 * Provides the problem corresponding to the current Task
	 * 
	 * @return the problem
	 */
	public Problem getProblem() {
		return problem;
	}

	/**
	 * Provides the value of the given option
	 * 
	 * @param optionName the name of the option
	 * @return the value associated with optionName (or null if there is none)
	 */
	public String getOptionValue(String optionName) {
		return options.get(optionName);
	}
}
