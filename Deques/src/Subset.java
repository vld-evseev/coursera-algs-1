import edu.princeton.cs.algs4.StdOut;

import static edu.princeton.cs.algs4.StdIn.*;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!isEmpty()) {
            String s = readString();
            rq.enqueue(s);
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}