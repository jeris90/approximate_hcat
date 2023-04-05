package appx.solver;

public class NumberSolution implements Solution{

	private double res;
	
	public NumberSolution(double val) {
		res = val;
	}
	
	@Override
	public String toString() {
		return Double.toString(res);
	}
	
	/**
	 * Provides the value of the solution
	 * @return the value of the solution
	 */
	public double getValue() {
		return res ;
	}
}
