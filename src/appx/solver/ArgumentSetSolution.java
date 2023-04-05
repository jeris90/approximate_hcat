package appx.solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of arguments (e.g. an extension of an AF)
 *
 * @param <T> the type of arguments
 */
public class ArgumentSetSolution implements Solution {

	/**
	 * The arguments in the solution
	 */
	private List<Integer> res;

	/**
	 * Creates an empty set solution
	 */
	public ArgumentSetSolution() {
		res = new ArrayList<Integer>();
	}

	/**
	 * Adds an argument to the current solution
	 * @param argument the new argument
	 */
	public void addArgumentInSolution(int argument) {
		res.add(argument);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0; i < res.size() - 1; i++) {
			builder.append(res.get(i) + ",");
		}
		builder.append(res.get(res.size() - 1));
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * Determines whether a given argument belongs to the solution
	 * @param argument the argument
	 * @return true iff argument belongs to the solution
	 */
	public boolean containsArgument(int argument) {
		return res.contains(argument);
	}

}
