package bilal.onlinecourses.algorithmpt1.prog.assignment2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *  Created by Bilal on 2/19/2016.
 *  The <tt>Subset</tt> class is a client program Subset.java that
 *  takes a command-line integer k; reads in a sequence of N strings
 *  from standard input using StdIn.readString();
 *  and prints out exactly k of them, uniformly at random.
 *  Each item from the sequence can be printed out at most once.
 *  Assume that 0 ≤ k ≤ N, where N is the number of string on standard input.
 *  <p>
 *  This implementation uses a dRandomizedQueue of strings
 *  </p>
 *
 *  @author Bilal Abdullah
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        for (int i = 0; i < k; i++)
            StdOut.printf("%s\n", queue.dequeue());
    }
}
