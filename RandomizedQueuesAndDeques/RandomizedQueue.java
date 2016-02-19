package bilal.onlinecourses.algorithmpt1.prog.assignment2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Created by Bilal on 2/18/2016.
 *  The <tt>RandomizedQueue</tt> class is a randomized queue
 *  similar to a stack or queue except that the item removed
 *  is chosen uniformly at random from items in the data structure
 *  It supports <em>enqueue</em>, <em>dequeue</em> and
 *  <em>sample</em> operations and iterating through the items
 *  in random order.
 *  <p>
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The <em>enqueue</em>, <em>size</em>, <em>sample</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case
 *  while <em>dequeue</em> operation most cM steps in the worst case,
 *  for some constant c.
 *  <p>
 *
 *  @author Bilal Abdullah
 *
 *  @param <Item> the generic type of an item in this bag
 */
public class RandomizedQueue<Item> implements Iterable<Item>  {

    private Item[] q;       // queue elements
    private int N;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        N = 0;
        first = 0;
        last = 0;
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;
    }

    // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }

    /**
     * Swap two values in the array
     * @param i index of first value
     * @param j index of second value
     * return without swap if both indexes are equal
     */
    private void swap(int i, int j)
    {
        if (i == j) return;
        Item t = q[j];
        q[j] = q[i];
        q[i] = t;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     * @throws NullPointerException if this item is null
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Null element!!!");
        // double size of array if necessary and recopy to front of array
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        N++;

        if (N > 2) {
            int ranIndex = StdRandom.uniform(N);

            int index = (first + ranIndex) % q.length;

            if (last == 0) swap(q.length - 1, index);
            else swap(last - 1, index);
        }
    }

    /**
     * Removes and return a random item
     *
     * @return the item on this queue that was randomly selected
     * @throws NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        Item item = q[first];
        q[first] = null;                            // to avoid loitering
        N--;
        first++;
        if (first == q.length) first = 0;           // wrap-around

        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return item;
    }

    /**
     * Return (but do not remove) a random item.
     *
     * @return the random item item on this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int ranIndex = StdRandom.uniform(N);
        Item item = q[(first + ranIndex) % q.length];
        return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    /**
     * Returns an independent iterator over items in random order
     *
     * @return an iterator that iterates over the items in this queue in random order
     */
    public Iterator<Item> iterator()  {
        return new RandomizedQueueIterator();
    }

    // doesn't implement remove()
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] array;
        private int i;

        RandomizedQueueIterator() {
            array = (Item[]) new Object[N];
            for (int j = 0; j < N; j++) {
                array[j] = q[(first + j) % q.length];
            }

            StdRandom.shuffle(array, 0, N - 1);
            i = 0;
        }

        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = array[i++];
            return item;
        }
    }

    /**
     * Unit tests the <tt>RandomizedQueue</tt> data type.
     *
     * @param  args command line parameters
     */
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        int testCount = 10;
        for (int i = 0; i < testCount; i++) {
            q.enqueue(Integer.toString(i));
        }

        StdOut.println("Print all items: " + q.toString());
        StdOut.println("Print all items: " + q.toString());
        StdOut.println("Print all items: " + q.toString());

        Iterator itr1 = q.iterator();
        Iterator itr2 = q.iterator();
        Iterator itr3 = q.iterator();

        while (itr1.hasNext())
        {
            StdOut.print(itr1.next().toString() +
                    itr2.next().toString() + itr3.next().toString() + " ");
        }


        StdOut.print("\nSample: ");
        for (int i = 0; i < testCount; i++) {
            StdOut.print(" " + q.sample());
        }

        StdOut.print("\nDequeue: ");
        for (int i = 0; i < testCount; i++) {
            StdOut.print(" " + q.dequeue());
        }

        StdOut.println("\nQueue Size: " + q.size());
    }
}
