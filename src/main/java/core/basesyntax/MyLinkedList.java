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
            prevTail.setNext(tail);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException("The index is greater than LL size");
        }
        MyNode<T> tempNode = head;
        int i = 0;
        while (i < index) {
            tempNode = tempNode.getNext();
            i++;
        }
        if (tempNode == null) {
            if (tail == null) {
                MyNode<T> newNode = new MyNode<>(head, tail, value);
                head = newNode;
                tail = newNode;
            } else {
                MyNode<T> prevTail = tail;
                tail = new MyNode<>(prevTail, null, value);
                prevTail.setNext(tail);
            }
        } else if (tempNode.getPrev() == null) {
            MyNode<T> newNode = new MyNode<>(null, tempNode, value);
            tempNode.setPrev(newNode);
            head = newNode;
        } else {
            MyNode<T> prevNode = tempNode.getPrev();
            MyNode<T> newNode = new MyNode<>(prevNode, tempNode, value);
            tempNode.setPrev(newNode);
            prevNode.setNext(newNode);
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
        return searchedNode.getValue();
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        MyNode<T> searchedNode = searchingByIndex(index);
        T oldValue = searchedNode.getValue();
        searchedNode.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        MyNode<T> searchedNode = searchingByIndex(index);
        MyNode<T> prevNode = searchedNode.getPrev();
        MyNode<T> nextNode = searchedNode.getNext();
        if (prevNode == null && nextNode == null) {
            head = null;
            tail = null;
        } else if (prevNode == null) {
            nextNode.setPrev(null);
            head = nextNode;
        } else if (nextNode == null) {
            prevNode.setNext(null);
            tail = prevNode;
        } else {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
        size--;
        return searchedNode.getValue();
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> tempNode = head;
        boolean isFound = false;
        for (int i = 0; i < size(); i++) {
            if (tempNode.getValue() == null ? object == null : tempNode.getValue().equals(object)) {
                isFound = true;
                break;
            }
            tempNode = tempNode.getNext();
        }
        if (!isFound) {
            return false;
        }
        MyNode<T> prevNode = tempNode.getPrev();
        MyNode<T> nextNode = tempNode.getNext();
        if (prevNode == null) {
            if (nextNode == null) {
                head = null;
                tail = null;
            } else {
                nextNode.setPrev(null);
                head = nextNode;
            }
        } else if (nextNode == null) {
            prevNode.setNext(null);
            tail = prevNode;
        } else {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
        size--;
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
        MyNode<T> tempNode = head;
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.getNext();
        }
        return tempNode;
    }
}
