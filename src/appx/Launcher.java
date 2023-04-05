package appx;

import java.util.LinkedList;

import appx.af.ArgumentationFramework;
import appx.extensionsemantics.exact.SimpleGroundedSemanticsSolver;
import appx.gradualsemantics.Categorizer;
import appx.parser.AFParser;
import appx.solver.Solution;
import appx.solver.Solver;
import appx.task.Problem;
import appx.task.Semantics;
import appx.task.Task;

public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArgumentationFramework af = AFParser.readingCNF("/Users/jdelobelle/Documents/recherche/approximation_semantique/instances/BA-numArg10-pbCycle0-0.cnf");
		
		/*af.add_attack(0, 1);
		af.add_attack(1, 0);
		af.add_attack(1, 2);
		*/
		System.out.println(af);
		
		Solver groundedSolver = new SimpleGroundedSemanticsSolver();
		Solution sol = groundedSolver.solve(new Task(Problem.SE, Semantics.GR), af);
		System.out.println(sol);
		
		/*
		Categorizer hcatSolver = new Categorizer();
		Solution sol_cat = hcatSolver.solve(new Task(Problem.SE, Semantics.GR), af);
		System.out.println(hcatSolver.printScores());
		*/
	}

}
