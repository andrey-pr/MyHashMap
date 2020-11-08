package org.scud.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List<E> {
    static class Node<E> {
        E val;
        Node<E> next = null;

        public Node(E val) {
            this.val = val;
        }
    }

    Node<E> root = null;

    @Override
    public int size() {
        int ret = 0;
        Node<E> p = root;
        while (p != null) {
            ret++;
            p = p.next;
        }
        return ret;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> p = root;
        while (p != null) {
            if (p.equals(o)) {
                return true;
            }
            p = p.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    class MyIterator implements Iterator<E> {
        int i = 0;
        @SuppressWarnings("unchecked")
        E[] arr = (E[]) toArray();

        @Override
        public boolean hasNext() {
            return i < arr.length;
        }

        @Override
        public E next() {
            return arr[i++];
        }
    }

    @Override
    public Object[] toArray() {
        Object[] tmp = new Object[size()];
        Node<E> p = root;
        int i = 0;
        while (p != null) {
            tmp[i++] = p.val;
            p = p.next;
        }
        return tmp;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (root == null) {
            root = new Node<>(e);
        } else {
            Node<E> p = root;
            while (p.next != null) {
                p = p.next;
            }
            p.next = new Node<>(e);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public E get(int index) {
        if (index >= size() || index < 0) {
            throw new IllegalArgumentException();
        }
        Node<E> p = root;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.val;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        if (index > size() || index < 0) {
            throw new IllegalArgumentException();
        }
        Node<E> trans = new Node<>(element);
        Node<E> p = root;
        for (int i = 1; i < index; i++) {
            p = p.next;
        }
        trans.next = p.next;
        p.next = trans;
    }

    @Override
    public E remove(int index) {
        if (index >= size() || index < 0) {
            throw new IllegalArgumentException();
        }
        E res;
        if (size() == 1) {
            res = root.val;
            root = null;
            return res;
        }
        Node<E> p = root;
        for (int i = 0; i < index - 1; i++) {
            p = p.next;
        }
        res = p.next.val;
        p.next = p.next.next;
        return res;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
