import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int N;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        capacity = 1;
        queue = (Item[]) new Object[capacity];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();

        if (N + 1 > capacity) {
            increaseSize();
        }
        queue[N++] = item;
    }

    private void increaseSize() {
        capacity *= 2;
        Item[] newQueue = (Item[]) new Object[capacity];
        int index = 0;
        for (Item i : queue) {
            newQueue[index++] = i;
        }
        queue = newQueue;
    }

    private void reduceSize() {
        capacity /= 2;
        Item[] newQueue = (Item[]) new Object[capacity];
        int index = 0;
        for (int i = 0; i < capacity; i++) {
            newQueue[index++] = queue[i];
        }
        queue = newQueue;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int i = StdRandom.uniform(N);
        Item ret = queue[i];
        queue[i] = queue[--N];
        queue[N] = null;
        if (capacity / 4 > N) {
            reduceSize();
        }
        return ret;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        return queue[StdRandom.uniform(N)];
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = 0;
        private int[] shuffledIndexes = new int[N];

        public boolean hasNext() {
            if (current == 0) {
                for (int i = 0; i < N; i++)
                    shuffledIndexes[i] = i;
                StdRandom.shuffle(shuffledIndexes);
            }
            return current < N;
        }

        public Item next() {
            if (current == 0) {
                for (int i = 0; i < N; i++)
                    shuffledIndexes[i] = i;
                StdRandom.shuffle(shuffledIndexes);
            }
            if (current >= N || size() == 0)
                throw new java.util.NoSuchElementException();

            return queue[shuffledIndexes[current++]];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}
