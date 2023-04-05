package appx.extensionsemantics.approximate;

import appx.af.ArgumentationFramework;
import appx.extensionsemantics.exact.SimpleGroundedSemanticsSolver;
import appx.gradualsemantics.Categorizer;
import appx.solver.ArgumentSetSolution;
import appx.solver.BinarySolution;
import appx.solver.NumberSolution;
import appx.solver.Solution;
import appx.solver.Solver;
import appx.task.Problem;
import appx.task.Semantics;
import appx.task.Task;

/**
 * This class implements a solver which follows this approach:
 * <ul>
 * <li>arguments in the grounded extension are accepted,</li>
 * <li>arguments attacked by the grounded are rejected,</li>
 * <li>other arguments are considered as accepted if their h-Categorizer value
 * is higher than a given threshold.</li>
 * </ul>
 * .
 *
 */
public class CategorizedBasedApproximateSolver extends Solver {
	/**
	 * The threshold for considering arguments as accepted
	 */
	private double threshold;

	/**
	 * Builds a solver
	 * 
	 * @param thresh 
	 */
	public CategorizedBasedApproximateSolver(double thresh) {
		this.threshold = thresh;
	}
	
	/**
	 * Builds a solver without specifying the threshold. 
	 * 
	 */
	public CategorizedBasedApproximateSolver() {
		this.threshold = -1;
	}
	
	/**
	 * This function allows the threshold to be adjusted according to a given problem and semantics.
	 * @param task
	 */
	private void choice_threshold(Task task) {
		
		if(task.getProblem() == Problem.DC) {
			switch(task.getSemantics()) {
				case CO:
				case ST:
				case SST:
					this.threshold = 0.5;
					break;
				case STG:
					this.threshold = 0;
					break;
				case ID:
					this.threshold = 1;
					break;
				default:
					System.out.println("Unknown semantics");
			}
		}
		
		if(task.getProblem() == Problem.DS) {
			switch(task.getSemantics()) {
				case PR:
				case SST:
				case STG:
					this.threshold = 1;
					break;
				case ST:
					this.threshold = 0;
					break;
				default:
					System.out.println("Unknown semantics");
			}
		}	
	}

	@Override
	public Solution solve(Task task, ArgumentationFramework af) {
		if (task.getProblem() != Problem.DC && task.getProblem() != Problem.DS) {
			throw new UnsupportedOperationException("This solver only supports DC and DS problems.");
		}
		Solver groundedSolver = new SimpleGroundedSemanticsSolver();
		String argumentName = task.getOptionValue("-a");
		if (argumentName == null) {
			throw new UnsupportedOperationException("This solver requires an argument name in the options.");
		}
		int argument = Integer.valueOf(argumentName);
		
		/* Grounded part */
		
		long temps_start = System.currentTimeMillis();
		Solution sol = groundedSolver.solve(new Task(Problem.SE, Semantics.GR), af);
		long temps_end = System.currentTimeMillis();
		System.out.print((temps_end - temps_start)/1000.+";");
		
		ArgumentSetSolution groundedExtension = (ArgumentSetSolution) sol;
		
		if (groundedExtension.containsArgument(argument)) {
			System.out.print("None;None;");
			return new BinarySolution(true);
		}
		for (int attacker : af.get_af_attacker()[argument]) {
			if (groundedExtension.containsArgument(attacker)) {
				System.out.print("None;None;");
				return new BinarySolution(false);
			}
		}
		
		
		/* h-cat part */

		Categorizer cat = new Categorizer();
		Task t = new Task(null, null);
		t.addOption("-a", argumentName);
		temps_start = System.currentTimeMillis();
		NumberSolution degree = (NumberSolution) cat.solve(t, af);
		temps_end = System.currentTimeMillis();
		System.out.print((temps_end - temps_start)/1000.+";");
		System.out.print(degree.getValue()+";"); /////////////
		
		choice_threshold(task);
		if (degree.getValue() >= threshold) {
			return new BinarySolution(true);
		} else {
			return new BinarySolution(false);
		}
	}

}
