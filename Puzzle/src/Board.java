import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;
import java.util.Random;

public class Board {

    private String outStr = "";
    private final short[][] blocks;
    private final int[][] goalState;
    private int N;
    private int manhattan = -1;
    private int hamming = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException();

        N = blocks.length;
        int goal = 1;

        goalState = new int[N][N];
        this.blocks = new short[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = (short) blocks[i][j];
                goalState[i][j] = goal++;
            }
        }

        goalState[N - 1][N - 1] = 0;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming != -1)
            return hamming;

        hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }

                if (blocks[i][j] != goalState[i][j])
                    hamming++;
            }
        }

        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan != -1)
            return manhattan;

        manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }

                int col = (blocks[i][j] - 1) / N;
                int row = (blocks[i][j] - 1) % N;
                manhattan += Math.abs(i - col) + Math.abs(j - row);
            }
        }

        System.out.println("");

        return manhattan;
    }

    public Board twin() {
        Random rand = new Random();
        int randomX1 = rand.nextInt(N - 1);
        int randomY1 = rand.nextInt(N - 1);

        int randomX2 = rand.nextInt(N - 1);
        int randomY2 = rand.nextInt(N - 1);

        int[][] copy = deepCopy();

        if (copy[randomX1][randomY1] == 0)
            randomX1++;

        if (copy[randomX2][randomY2] == 0)
            randomX2++;

        if (randomX1 == randomX2 && randomY1 == randomY2) {
            if (randomX1 + 1 >= N - 1 || copy[randomX1 + 1][randomY1] == 0) {
                randomY1++;
            } else {
                randomX1++;
            }
        }

        int temp = copy[randomX1][randomY1];
        copy[randomX1][randomY1] = copy[randomX2][randomY2];
        copy[randomX2][randomY2] = temp;

        Board twin = new Board(copy);

        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board other = (Board) y;
        if (other.dimension() != this.dimension()) return false;

        return Arrays.equals(blocks, other.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    boolean[] directionExists = directionsExisted(i, j);
                    for (int k = 0; k < directionExists.length; k++) {
                        if (directionExists[k])
                            neighbors.push(createNeighbor(i, j, k));
                    }

                }
            }
        }
        return neighbors;
    }

    private Board createNeighbor(int ex, int ey, int direction) {
        final int[][] neighbor = deepCopy();

        int north = ex - 1;
        int south = ex + 1;
        int west = ey - 1;
        int east = ey + 1;

        switch (direction) {
            case 0:
                neighbor[ex][ey] = neighbor[north][ey];
                neighbor[north][ey] = 0;
                break;
            case 1:
                neighbor[ex][ey] = neighbor[south][ey];
                neighbor[south][ey] = 0;
                break;
            case 2:
                neighbor[ex][ey] = neighbor[ex][west];
                neighbor[ex][west] = 0;
                break;
            case 3:
                neighbor[ex][ey] = neighbor[ex][east];
                neighbor[ex][east] = 0;
                break;
            default:
                break;
        }

        return new Board(neighbor);
    }

    private int[][] deepCopy() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    private boolean[] directionsExisted(int ex, int ey) {
        boolean[] directions = new boolean[4];
        for (int i = 0; i < directions.length; i++) {
            directions[i] = false;
        }

        int north = ex - 1;
        int south = ex + 1;
        int west = ey - 1;
        int east = ey + 1;

        if (north >= 0)
            directions[0] = true;

        if (south <= N - 1)
            directions[1] = true;

        if (west >= 0)
            directions[2] = true;

        if (east <= N - 1)
            directions[3] = true;

        return directions;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        if (outStr.length() > 0) {
            return outStr;
        }

        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }

        outStr = s.toString();
        return outStr;
    }

    public static void main(String... args) {
        String path = "C:\\Users\\Vlad\\Documents\\coursera-algs-1\\Puzzle\\8puzzle-testing\\puzzle2x2-07.txt";
        In in = new In(path);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial.toString());
        System.out.println(initial.manhattan());

    }
}