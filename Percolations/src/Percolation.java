import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int N;

	private byte closed = 0;
	private byte opened = 1;
	private int qusize;
	private boolean backlashed = false;

	private byte[] bottomSitesArr;

	private byte[][] grid;
	private WeightedQuickUnionUF quickUnion;

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		if (N <= 0)
			throw new java.lang.IllegalArgumentException();
		this.N = N;
		qusize = N * N + 2;

		grid = new byte[N][N];
		bottomSitesArr = new byte[N];
		
		quickUnion = new WeightedQuickUnionUF(qusize);

		int k = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				grid[i][j] = closed;
			}
			bottomSitesArr[k++] = closed;
		}
	}

	// open site (row i, column j) if it is not open already
	// WQU: union
	public void open(int i, int j) {
		checkRange(i, j);
		grid[i - 1][j - 1] = opened;

		if (grid.length > 1 && j < N) {
			if (isOpen(i, j + 1) && !quickUnion.connected(xyTo1D(i, j + 1) - N, xyTo1D(i, j) - N)) {
				quickUnion.union(xyTo1D(i, j + 1) - N, xyTo1D(i, j) - N);
			}
		}

		if (grid.length > 1 && i < N) {
			if (isOpen(i + 1, j) && !quickUnion.connected(xyTo1D(i, j) - N, xyTo1D(i + 1, j) - N)) {
				quickUnion.union(xyTo1D(i, j) - N, xyTo1D(i + 1, j) - N);
			}
		}

		if (grid.length > 1 && j - 1 > 0) {
			if (isOpen(i, j - 1) && !quickUnion.connected(xyTo1D(i, j - 1) - N, xyTo1D(i, j) - N)) {
				quickUnion.union(xyTo1D(i, j - 1) - N, xyTo1D(i, j) - N);
			}
		}

		if (grid.length > 1 && i - 1 > 0) {
			if (isOpen(i - 1, j) && !quickUnion.connected(xyTo1D(i - 1, j) - N, xyTo1D(i, j) - N)) {
				quickUnion.union(xyTo1D(i - 1, j) - N, xyTo1D(i, j) - N);
			}
		}

		if (i == 1) {
			quickUnion.union(qusize - 2, xyTo1D(i - 1, j));
		}

		if (i == N) {
			bottomSitesArr[j - 1] = opened;
		} 
		
		if (!percolates()) {
			for (int k = 0; k < bottomSitesArr.length; k++) {
				if (bottomSitesArr[k] == opened
						&& quickUnion.connected(xyTo1D(i - 1, j), index1Dto2D(k))) {
										
					if (!percolates() && !backlashed 
							&& quickUnion.connected(qusize - 2, index1Dto2D(k))) {
						
						quickUnion.union(qusize - 1, index1Dto2D(k));
						backlashed = true;
					}
				}
			}
		}
	}
	
	
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		checkRange(i, j);
		return grid[i - 1][j - 1] == opened;
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		checkRange(i, j);
		return isOpen(i, j)
				&& quickUnion.connected(xyTo1DNorm(i - 1, j - 1), qusize - 2);
	}

	// does the system percolate?
	public boolean percolates() {
		return quickUnion.connected(qusize - 1, qusize - 2);
	}

	private void checkRange(int i, int j) {
		if (i < 1 || i > this.N || j < 1 || j > this.N)
			throw new java.lang.IndexOutOfBoundsException();
	}

	private int xyTo1D(int x, int y) {
		return (N * x) + y - 1;
	}

	private int xyTo1DNorm(int x, int y) {
		return (N * x) + y;
	}
	
	private int index1Dto2D(int k) {
		return N*N-N+k;
	}

	// test
	public static void main(String[] args) {

	}
}
