package bilal.onlinecourses.algorithmpt1.prog.assignment2;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Created by Bilal on 2/18/2016.
 *  The <tt>Dequeue</tt> class is a generalization of a stack
 *  and a queue that supports adding and removing items from
 *  either the front or the back of the data structure.
 *  It supports <em>addFirst</em>, <em>addLast</em>, <em>removeFirst</em>
 *  and <em>removeLast</em> operations and iterating
 *  through the items in FIFO order.
 *  <p>
 *  This implementation uses a doubly-linked list with a static nested class for
 *  linked-list nodes.
 *  The <em>addFirst</em>, <em>addLast</em>, <em>removeFirst</em>,
 *  <em>removeLast</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  </p>
 *
 *  @author Bilal Abdullah
 *
 *  @param <Item> the generic type of an item in this bag
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of deque
    private Node<Item> last;     // end of deque
    private int N;               // number of elements on the deque

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    /**
     * Initializes an empty deque.
     */
    public Deque() {
        first = null;
        last  = null;
        N = 0;
    }

    /**
     * Returns true if this deque is empty.
     *
     * @return <tt>true</tt> if this deque is empty; <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this deque.
     *
     * @return the number of items in this deque
     */
    public int size() {
        return N;
    }

    /**
     * Add the item to the front of the deque
     *
     * @param  item the item to add to the front of the deque
     * @throws NullPointerException if this item is null
     */
    public void addFirst(Item item)
    {
        if (item == null) throw new NullPointerException("Null element!!!");
        Node<Item> newNode = new Node<Item>();
        newNode.item = item;
        newNode.next = null;
        if (isEmpty()) last = newNode;
        else first.previous = newNode;
        newNode.next = first;
        first = newNode;
        N++;
    }

    /**
     * Add the item to the end of the deque
     *
     * @param  item the item to add to the end of the deque
     * @throws NullPointerException if this item is null
     */
    public void addLast(Item item)
    {
        if (item == null) throw new NullPointerException("Null element!!!");
        Node<Item> newNode = new Node<Item>();
        newNode.item = item;
        newNode.next = null;
        if (isEmpty()) first = newNode;
        else {
            newNode.previous = last;
            last.next = newNode;
        }
        last = newNode;
        N++;
    }

    /**
     * Remove and return the item from the front
     *
     * @return the item from the front of the deque
     * @throws NoSuchElementException if this deque is empty
     */
    public Item removeFirst()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        if (first.next == null) last = null;
        else first.next.previous = null;
        first = first.next;
        N--;
        return item;
    }

    /**
     * Remove and return the item from the end
     *
     * @return the item from the end of the deque
     * @throws NoSuchElementException if this deque is empty
     */
    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        if (first.next == null) first = null;
        else last.previous.next = null;
        last = last.previous;
        N--;
        return item;
    }

    /**
     * Returns a string representation of this deque.
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
     * Returns an iterator that iterates over the items in this deque in FIFO order.
     *
     * @return an iterator that iterates over the items in this deque in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new DequeIterator<Item>(first);
    }

    // doesn't implement remove()
    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    /**
     * Unit tests the <tt>Deque</tt> data type.
     *
     * @param  args command line parameters
     *
     */
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();

        int testCount = 10;
        for (int i = 1; i <= testCount; i++) {
            q.addFirst(Integer.toString(i));
            q.addLast(Integer.toString(i * i));
        }

        StdOut.println("Print all items: " + q.toString());

        StdOut.print("Remove first: ");
        for (int i = 0; i < testCount; i++) {
            StdOut.print(q.removeFirst() + " ");
        }

        StdOut.print("\nRemove end: ");
        for (int i = 0; i < testCount; i++) {
            StdOut.print(q.removeLast() + " ");
        }
    }
}
