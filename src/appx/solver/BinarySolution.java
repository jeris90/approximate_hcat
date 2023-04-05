package appx.solver;

public class BinarySolution implements Solution {

	private boolean res;
	
	public BinarySolution(boolean b) {
		this.res = b;
	}
	
	@Override
	public String toString() {
		return res? "YES": "NO";
	}
}
