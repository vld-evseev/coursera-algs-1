import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Item getItem() {
            return item;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setItem(Item i) {
            this.item = i;
        }

        public void setNext(Node n) {
            this.next = n;
        }

        public void setPrev(Node p) {
            this.prev = p;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (first == null)
            return true;
        return false;
    }

    // return the number of items on the deque
    public int size() {
        if (isEmpty()) return 0;
        int count = 1;
        Node n = first;
        while (n != last) {
            count++;
            n = n.next;
        }
        return count;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();

        Node n = new Node();
        n.item = item;
        if (first != null) {
            n.next = first;
            first.prev = n;
            n.prev = null;
            first = n;
        } else {
            first = n;
            last = n;
            n.prev = null;
            n.next = null;
        }
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();

        Node n = new Node();
        n.item = item;
        if (last != null) {
            n.prev = last;
            last.next = n;
            n.next = null;
            last = n;
        } else {
            first = n;
            last = n;
            n.prev = null;
            n.next = null;
        }
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (first == null)
            throw new java.util.NoSuchElementException();

        Item n = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        return n;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (last == null)
            throw new java.util.NoSuchElementException();

        Item n = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return n;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}