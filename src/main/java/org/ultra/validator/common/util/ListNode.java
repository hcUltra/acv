package org.ultra.validator.common.util;

import java.util.*;


import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author hcUltra
 * @description 该类的方法只是为了生成随机参数而做出的特殊适配，在练习算法过程中并不推荐直接使用
 * @date 2024/5/2 12:42
 **/
public class ListNode<T> implements Collection<Integer> {
    public T val = null;
    public ListNode next = null;

    public ListNode() {
    }

    public ListNode(T val) {
        this.val = val;
    }

    public ListNode(T val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public int size() {
        int size = this.val == null ? 0 : 1;
        ListNode current = this.next;  // Start from the first actual node
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.next == null;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Integer) || this.val == null) {
            return false;
        }
        ListNode current = this.next;
        while (current != null) {
            if (current.val.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private ListNode currentNode = ListNode.this;

            @Override
            public boolean hasNext() {
                return currentNode.next != null;
            }

            @Override
            public Integer next() {
                if (currentNode.next == null) {
                    throw new NoSuchElementException();
                }
                T value = (T) currentNode.val;
                currentNode = currentNode.next;
                return (Integer) value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        int i = 0;
        ListNode current = this.next;
        while (current != null) {
            result[i++] = current.val;
            current = current.next;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        ListNode current = this.next;
        while (current != null) {
            a[i++] = (T) current.val;
            current = current.next;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(Integer e) {
        if (e == null) {
            return false;
        }
        ListNode current = this;
        while (current.next != null) {
            current = current.next;
        }
        if (val == null) {
            val = (T) e;
        } else {
            current.next = new ListNode(e);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }
        ListNode current = this.next;
        ListNode prev = this;
        while (current != null) {
            if (current.val.equals(o)) {
                prev.next = current.next;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        boolean modified = false;
        for (Integer e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        this.next = null;  // Effectively clears the list
        this.val = null;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        ListNode current = this.next;
        ListNode prev = this;
        while (current != null) {
            if (!c.contains(current.val)) {
                prev.next = current.next;
                modified = true;
            } else {
                prev = current;
            }
            current = prev.next;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        ListNode current = this.next;
        ListNode prev = this;
        while (current != null) {
            if (c.contains(current.val)) {
                prev.next = current.next;
                modified = true;
            } else {
                prev = current;
            }
            current = prev.next;
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode current = this;
        sb.append('[');
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode<?> listNodeA = (ListNode<?>) o;
        ListNode<?> listNodeB = this;
        if (listNodeA.size() != size()) {
            return false;
        }
        while (listNodeA.next != null) {
            if (!listNodeA.val.equals(listNodeB.val)) {
                return false;
            }
            listNodeA = listNodeA.next;
            listNodeB = listNodeB.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, next);
    }

    // for test
    public static void main(String[] args) {
        ListNode<Integer> listNode1 = new ListNode<>(5);
        listNode1.add(4);
        listNode1.add(3);
        listNode1.add(2);
        listNode1.add(1);

        ListNode<Integer> listNode2 = new ListNode<>(5);
        listNode2.add(4);
        listNode2.add(3);
        listNode2.add(2);
        listNode2.add(1);

        System.out.println(listNode1.equals(listNode2));

    }
}

