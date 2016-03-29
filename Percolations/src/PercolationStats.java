import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
		
	private double[] threshold;
	private int times;
		
	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int n, int t) {
		if (n <= 0 || t <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		threshold = new double[t];
		times = t;
		
		for (int i = 0; i < t; i++) {
			int count = 0;
			Percolation p = new Percolation(n);
			while(!p.percolates()){
				int j = StdRandom.uniform(1, n + 1);
				int k = StdRandom.uniform(1, n + 1);
				if (!p.isOpen(j, k)) {
					p.open(j, k);
					++count;
				}
			}
			threshold[i] = (double) count / (double) (n * n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(threshold);
	}
	
	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(threshold);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - stddev() * 1.96d/Math.sqrt(times);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + stddev() * 1.96d/Math.sqrt(times);
	}
	
	public static void main(String[] args) {
		int n = Integer.valueOf(args[0]);
		int t = Integer.valueOf(args[1]);
		
		PercolationStats ps = new PercolationStats(n, t);
	
		StdOut.println("mean = " + ps.mean());
		StdOut.println("stddev = " + ps.stddev());
		StdOut.println("95% confidence interval = " 
		+ ps.confidenceLo() + ", " + ps.confidenceHi());
		
	}

}
