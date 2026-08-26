package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> head;
    private MyNode<T> tail;

    public MyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            MyNode<T> newNode = new MyNode<>(head, tail, value);
            head = newNode;
            tail = newNode;
        } else {
            MyNode<T> prevTail = tail;
            tail = new MyNode<>(prevTail, null, value);
            prevTail.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException("The index is greater than LL size");
        }
        MyNode<T> currentNode = head;
        int i = 0;
        while (i < index) {
            currentNode = currentNode.next;
            i++;
        }
        if (currentNode == null) {
            if (tail == null) {
                MyNode<T> newNode = new MyNode<>(head, tail, value);
                head = newNode;
                tail = newNode;
            } else {
                MyNode<T> prevTail = tail;
                tail = new MyNode<>(prevTail, null, value);
                prevTail.next = tail;
            }
        } else if (currentNode.prev == null) {
            MyNode<T> newNode = new MyNode<>(null, currentNode, value);
            currentNode.prev = newNode;
            head = newNode;
        } else {
            MyNode<T> prevNode = currentNode.prev;
            MyNode<T> newNode = new MyNode<>(prevNode, currentNode, value);
            currentNode.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        MyNode<T> searchedNode = searchingByIndex(index);
        return searchedNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        MyNode<T> searchedNode = searchingByIndex(index);
        T oldValue = searchedNode.value;
        searchedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        MyNode<T> currentNode = searchingByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> currentNode = head;
        boolean found = false;
        for (int i = 0; i < size(); i++) {
            if (currentNode.value == null ? object == null : currentNode.value.equals(object)) {
                found = true;
                break;
            }
            currentNode = currentNode.next;
        }
        if (!found) {
            return false;
        }
        unlink(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size() - 1) {
            throw new ArrayIndexOutOfBoundsException("The index is greater than LL size");
        }
    }

    private MyNode<T> searchingByIndex(int index) {
        MyNode<T> currentNode = head;
        if (index > size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unlink(MyNode<T> currentNode) {
        MyNode<T> prevNode = currentNode.prev;
        MyNode<T> nextNode = currentNode.next;
        if (prevNode == null) {
            if (nextNode == null) {
                head = null;
                tail = null;
            } else {
                nextNode.prev = null;
                head = nextNode;
            }
        } else if (nextNode == null) {
            prevNode.next = null;
            tail = prevNode;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    private static class MyNode<T> {
        private MyNode<T> prev;
        private MyNode<T> next;
        private T value;

        public MyNode(MyNode<T> prev, MyNode<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
