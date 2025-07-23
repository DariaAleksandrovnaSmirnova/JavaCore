package javacore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomLinkedListTest {
    private CustomLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<>();
    }

    @Test
    void testEmptyList() {
        assertEquals(0, list.size());
    }

    @Test
    void testListSize() {
        list.addLast(7);
        list.addLast(0);
        assertEquals(2, list.size());
    }

    @Test
    void testAddFirst() {
        list.addFirst(7);
        assertEquals(1, list.size());
        assertEquals(7, list.getFirst());
        assertEquals(7, list.getLast());

        list.addFirst(4);
        assertEquals(2, list.size());
        assertEquals(4, list.getFirst());
        assertEquals(7, list.getLast());
    }

    @Test
    void testAddLast() {
        list.addLast(9);
        assertEquals(1, list.size());
        assertEquals(9, list.getFirst());
        assertEquals(9, list.getLast());

        list.addLast(2);
        assertEquals(2, list.size());
        assertEquals(9, list.getFirst());
        assertEquals(2, list.getLast());
    }

    @Test
    void testAddAtIndex() {
        list.addLast(5);
        list.add(1, 3);
        list.add(0, 2);

        assertEquals(3, list.size());
        assertEquals(2, list.get(0));
        assertEquals(5, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testGet() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testGetInvalidIndex(){
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    void testGetNothing() {
        assertThrows(NoSuchElementException.class, () -> list.getFirst());
    }

    @Test
    void testRemoveFirst() {
        list.addLast(1);
        list.addLast(2);

        assertEquals(1, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals(2, list.getFirst());

        assertEquals(2, list.removeFirst());
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveLast() {
        list.addLast(1);
        list.addLast(2);

        assertEquals(2, list.removeLast());
        assertEquals(1, list.size());
        assertEquals(1, list.getLast());

        assertEquals(1, list.removeLast());
        assertEquals(0, list.size());
    }

    @Test
    void testRemoveAtIndex() {
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }
}