package javacore;

import java.util.NoSuchElementException;

public class CustomLinkedList<E> {
    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size;

    public int size() {
        return size;
    }

    public void addFirst(E el) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(el, f, null);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    public void addLast(E el) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(el, null, l);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void add(int index, E el) {
        if (index == size) {
            addLast(el);
        } else {
            Node<E> succ = findNodeByIndex(index);
            Node<E> pred = succ.prev;
            Node<E> newNode = new Node<>(el, succ, pred);
            succ.prev = newNode;
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    public E getFirst() {
        Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException();
        }
        return f.element;
    }

    public E getLast() {
        Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException();
        }
        return l.element;
    }

    public E get(int index) {
        checkPositionIndex(index);
        return findNodeByIndex(index).element;
    }

    public E removeFirst() {
        E el = first.element;
        Node<E> next = first.next;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        return el;
    }

    public E removeLast() {
        E el = last.element;
        Node<E> prev = last.prev;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        return el;
    }

    public E remove(int index) {
        checkPositionIndex(index);

        Node<E> node = findNodeByIndex(index);
        E element = node.element;
        Node<E> next = node.next;
        Node<E> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.element = null;
        size--;
        return element;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkPositionIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size));
        }
    }

    private Node<E> findNodeByIndex(int index) {
        Node<E> x;
        if (index < (size >> 1)) {
            x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
        } else {
            x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
        }
        return x;
    }
}
