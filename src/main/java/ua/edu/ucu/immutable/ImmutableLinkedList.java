package ua.edu.ucu.immutable;

public final class ImmutableLinkedList implements ua.edu.ucu.collections.immutable.ImmutableList {
    private int length;
    private Node head;

    private static class Node {
        private Object data;
        private Node next;
        private Node prev;

        Node(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setNext(Node newNode) {
            this.next = newNode;
        }

        public void setPrev(Node newNode) {
            this.prev = newNode;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object dataSet) {
            data = dataSet;
        }

    }

    public ImmutableLinkedList() {

    }

    private ImmutableLinkedList(Node head) {
        this.head = head;
        Node temp = head;
        int cnt = 0;
        while (temp != null) {
            temp = temp.getNext();
            cnt++;
        }
        length = cnt;
    }

    public ImmutableLinkedList(Object[] news) {
        ImmutableLinkedList lst = new ImmutableLinkedList();
        lst = lst.addAll(news);
        head = lst.getHead();
        length = lst.size();
    }

    private Node getHead() {
        return head;
    }


    private Node copy() {
        if (head == null) {
            return null;
        }
        Node copyhead = new Node(head.getData());
        Node copyTemp = copyhead;
        Node temp = head;
        while (temp.getNext() != null) {
            Node nxt = new Node(temp.getNext().getData());
            copyTemp.setNext(nxt);
            nxt.setPrev(copyTemp);
            copyTemp = copyTemp.getNext();
            temp = temp.getNext();
        }
        return copyhead;
    }


    @Override
    public ImmutableLinkedList add(Object e) {
        return add(length, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(length, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }


        Node newHead = copy();
        if (c.length == 0) {
            return new ImmutableLinkedList(newHead);
        }
        int startFrom;
        if (index == 0) {
            startFrom = 1;
        } else {
            startFrom = 0;
        }

        if (startFrom == 1) {
            newHead = addFirst(c[0]).getHead();
        }

        Node temp = newHead;

        for (int i = 0; i < index - 1 + startFrom; i++) {
            temp = temp.getNext();
        }

        for (int i = startFrom; i < c.length; i++) {
            Node newNode = new Node(c[i]);
            Node nxt = temp.getNext();
            temp.setNext(newNode);
            newNode.setPrev(temp);
            newNode.setNext(nxt);
            if (nxt != null) {
                nxt.setPrev(newNode);
            }
            temp = newNode;
        }
        return new ImmutableLinkedList(newHead);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getData();
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFirst();
        }
        Node headCopy = copy();
        Node temp = headCopy;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        temp.getPrev().setNext(temp.getNext());

        return new ImmutableLinkedList(headCopy);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Node headCopy = copy();
        Node temp = headCopy;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        temp.setData(e);
        return new ImmutableLinkedList(headCopy);
    }

    @Override
    public int indexOf(Object e) {
        Node temp = head;
        for (int i = 0; i < size(); i++) {
            if (temp.getData().equals(e)) {
                return i;
            }
            temp = temp.getNext();
        }
        return -1;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[length];
        for (int i = 0; i < length; i++) {
            result[i] = get(i);
        }
        return result;
    }

    public ImmutableLinkedList addFirst(Object e) {
        Node copyHead = copy();
        Node newNode = new Node(e);
        if (copyHead == null) {
            copyHead = newNode;
        } else {
            newNode.setNext(copyHead);
            copyHead.setPrev(newNode);
            copyHead = newNode;
        }
        return new ImmutableLinkedList(copyHead);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(length, e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(length - 1);
    }

    public ImmutableLinkedList removeFirst() {
        if (length == 0) {
            throw new IndexOutOfBoundsException();
        }
        Node headCopy = copy();
        headCopy = headCopy.getNext();
        return new ImmutableLinkedList(headCopy);

    }

    public ImmutableLinkedList removeLast() {
        return remove(length - 1);

    }
}