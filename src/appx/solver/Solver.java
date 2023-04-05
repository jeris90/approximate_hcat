package appx.solver;

import appx.af.ArgumentationFramework;
import appx.task.Task;

public abstract class Solver {

	//private Task task;

	// private Solution solution;

	public abstract Solution solve(Task task, ArgumentationFramework af);
}
