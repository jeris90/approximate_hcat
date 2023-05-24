package appx.gradualsemantics;

import appx.af.ArgumentationFramework;
import appx.solver.NumberSolution;
import appx.solver.Solution;
import appx.solver.Solver;
import appx.task.Task;


/**
 * This class solves the reasoning tasks related to the h-categorizer semantics. 
 */
public class Categorizer extends Solver {

	/* An array containing the score of each argument returned by the h-categorizer semantics. */
	private double[] scores;
	
	/* An epsilon value used for the fixed point method. */
	private final static double EPSILON = 0.0001;
	
	
	private double[] initScores(ArgumentationFramework af) {
		
		double[] scores_arguments = new double[af.nb_arguments()];
		
		for(int i = 0; i<scores_arguments.length ; i++) {
			scores_arguments[i] = 1.;
		}
		return scores_arguments;
	}
	
	
	private static boolean stabilisation(double[] tab1, double[] tab2) {
		for(int i = 0 ; i < tab1.length ; i++) {
			if(Math.abs(tab1[i]-tab2[i]) > EPSILON) {
				return false;
			}
		}
		return true;
	}
	
	private double[] computeOneStep(ArgumentationFramework af, double[] scoresArg) {
		double[] res = new double[scoresArg.length];
		
		for (int i = 0; i < scoresArg.length; i++) {
			double sumScoreAttacker = 0.;
			for (int attacker : af.get_af_attacker()[i]) {
				sumScoreAttacker += scoresArg[attacker];
			}
			res[i] = 1. / (1. + sumScoreAttacker);
			
		}
		return res;
	}
	
	
	private double[] computeFinalScore(ArgumentationFramework af) {
		double[] res = initScores(af);
		boolean hasChanged = true;
		while (hasChanged) {
			double[] newScores = computeOneStep(af,res);
			if (stabilisation(res,newScores)) {
				hasChanged = false;
			}
			res = newScores;
		}
		return res;
	}
	
	public double[] getScores() {
		return scores;
	}
	
	public String printScores() {
		StringBuilder build = new StringBuilder("[");
		for(int i = 0 ; i<scores.length - 1 ; i++) {
			build.append(scores[i] + ",");
		}
		build.append(scores[scores.length - 1] + "]");
		return build.toString();
	}
	
	@Override
	public  Solution solve(Task task, ArgumentationFramework af) {
		
		Solution solution = null;
		
		scores = computeFinalScore(af);
		
		String argumentName = task.getOptionValue("-a");
		if (argumentName == null) {
			throw new UnsupportedOperationException(
					task.getProblem().toString() + " requires an argument name in the options.");
		}
		int index_argument = af.getArgumentFromName(argumentName);
		
		
		solution = new NumberSolution(scores[index_argument]);
		
		return solution;
	}
	
}
