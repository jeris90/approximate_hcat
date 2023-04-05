package appx.extensionsemantics.exact;

import appx.af.ArgumentationFramework;
import appx.solver.ArgumentSetSolution;
import appx.solver.BinarySolution;
import appx.solver.Solution;
import appx.solver.Solver;
import appx.task.Problem;
import appx.task.Task;


/**
 * This class solves the reasoning tasks related to the grounded semantics
 * thanks to the characteristic function. Arguments are associated with a
 * three-valued labelling.
 *
 */
public class SimpleGroundedSemanticsSolver extends Solver{

	/**
	 * Represents the status of an argument with respect to the grounded
	 * semantics.<br/>
	 * <em>This enum will probably be moved to become public.</em>
	 * 
	 *
	 */
	private enum Label {
		IN, OUT, UNDEC;
	}

	/**
	 * The labelling used for computing the grounded extension
	 */
	private Label[] labelling;

	@Override
	public Solution solve(Task task, ArgumentationFramework af) {

		labelling = initLabelling(af);

		boolean hasChanged = true;
		while (hasChanged) {
			Label[] newLabelling = propagateDefense(af, labelling);
			if (sameLabelling(newLabelling, labelling)) {
				hasChanged = false;
			}
			labelling = newLabelling;
		}

		if (task.getProblem().equals(Problem.SE)) {

			ArgumentSetSolution solution = new ArgumentSetSolution();
			for (int i = 0; i < labelling.length; i++) {
				if (labelling[i].equals(Label.IN)) {
					solution.addArgumentInSolution(i);
				}
			}
			return solution;
		} else if (task.getProblem().equals(Problem.DC) || task.getProblem().equals(Problem.DS)) {
			String argumentName = task.getOptionValue("-a");
			if (argumentName == null) {
				throw new UnsupportedOperationException(
						task.getProblem().toString() + " requires an argument name in the options.");
			}
			int index_argument = Integer.valueOf(argumentName);
			
			/*if (argument == null) {
				throw new UnsupportedOperationException("The argument name " + argumentName + " is not recognized.");
			}*/
			return new BinarySolution(labelling[index_argument].equals(Label.IN));
		}
		throw new UnsupportedOperationException(
				"Task " + task.getProblem() + " is not supported by SimpleGroundedSemanticsSolver.");
	}

	/**
	 * Initializes the labelling for computing the grounded extension. All
	 * unattacked arguments are IN, other arguments are UNDEC.
	 * 
	 * @param af  the argumentation framework
	 * @return the initialized labelling where unattacked arguments are IN
	 */
	private Label[] initLabelling(ArgumentationFramework af) {
		Label[] labelling = new Label[af.nb_arguments()];
		for (int i = 0; i < labelling.length; i++) {
			if (af.get_af_attacker()[i].isEmpty()) {
				labelling[i] = Label.IN;
			} else {
				labelling[i] = Label.UNDEC;
			}
		}
		return labelling;
	}

	/**
	 * Applies the characteristic function to the current labelling
	 * 
	 * @param af        the argumentation framework
	 * @param labelling the current labelling
	 * @return the result of the characteristic function applied to the current
	 *         labelling
	 */
	private Label[] propagateDefense(ArgumentationFramework af, Label[] labelling) {
		Label[] result = new Label[labelling.length];
		for (int i = 0; i < labelling.length; i++) {
			result[i] = Label.UNDEC;
		}

		// We check all the arguments of the AF and if an argument has the label IN then all the arguments it attacks have the label OUT.
		for (int i = 0; i < labelling.length; i++) {
			if (labelling[i].equals(Label.IN)) {
				result[i] = Label.IN;
				for (int argument : af.get_af_attackee()[i]) {
					result[argument] = Label.OUT;
				}
			}
		}

		for (int i = 0; i < labelling.length; i++) {
			if (result[i].equals(Label.UNDEC) && allAttackersAreOut(af, labelling, i)) {
				result[i] = Label.IN;
			}
		}
		return result;
	}

	/**
	 * Provides a String representation of a labelling
	 * 
	 * @param labelling the labelling
	 * @return the string representation
	 */
	private String labellingToString(Label[] labelling) {
		StringBuilder build = new StringBuilder("[");
		for (int i = 0; i < labelling.length - 1; i++) {
			build.append(labelling[i].toString() + ",");
		}
		build.append(labelling[labelling.length - 1].toString() + "]");
		return build.toString();
	}

	/**
	 * Checks whether two labellings are identical
	 * 
	 * @param labelling1 the first labelling
	 * @param labelling2 the second labelling
	 * @return true iff labelling1 is the same as labelling2
	 */
	private boolean sameLabelling(Label[] labelling1, Label[] labelling2) {
		if (labelling1.length != labelling2.length) {
			return false;
		}
		for (int i = 0; i < labelling1.length; i++) {
			if (labelling1[i] != labelling2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks whether all the attackers of a given argument are OUT.
	 * 
	 * @param af        the argumentation framework
	 * @param labelling the current labelling
	 * @param index     the index of the query argument
	 * @return true if and only if all the attackers of the argument at position
	 *         index are OUT
	 */
	private boolean allAttackersAreOut(ArgumentationFramework af, Label[] labelling, int index) {
		for (int attacker : af.get_af_attacker()[index]) {
			if (!labelling[attacker].equals(Label.OUT)) {
				return false;
			}
		}
		return true;
	}
}
