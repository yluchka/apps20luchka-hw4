package ua.edu.ucu.immutable;


import ua.edu.ucu.immutable.ImmutableLinkedList;

import java.util.*;

public class Queue implements Iterable<String>{
    private ImmutableLinkedList elements;

    public Queue() {
        elements = new ImmutableLinkedList();
    }

    public Queue(Object e) {
        elements = new ImmutableLinkedList(new Object[]{e});
    }

    public Queue(Object[] data) {
        elements = new ImmutableLinkedList(data);
    }

    public Object peek() {
        if (elements.size() == 0) {
            throw new IndexOutOfBoundsException();
        }
        return elements.getFirst();
    }

    public Object dequeue() {
        Object result = peek();
        elements.removeLast();
        return result;
    }

    public void enqueue(Object e) {
        elements = elements.addFirst(e);
    }


    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public Iterator<String> iterator() {
        String[] array = Arrays.copyOf(toArray(), elements.size(), String[].class);
        List<String> list = Arrays.asList(array);
        Collections.reverse(list);
        return list.iterator();
    }
}