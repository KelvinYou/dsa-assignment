/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

import java.util.Iterator;
/**
 *
 * @author Acer
 */
public class DoublyLinkedList<T> implements ListInterface<T> {
    private Node firstNode;                     // reference to first node
    private Node lastNode;                      // reference to last node
    private int num;

    
    @Override
    public void clear() {
        firstNode = lastNode = null;    // set first and last node to empty
        num = 0;                        // set number of size to 0
    }
    
    @Override
    public boolean add(T newElement) {
        if (newElement != null) {
            Node newNode = new Node(newElement);

            if (isEmpty()) {
                firstNode = newNode;        // set new node if empty
                lastNode = newNode;
            } else {
                newNode.prev = lastNode;    // arrange the node
                lastNode.next = newNode;
                lastNode = newNode;
            }

            num++;
        }
        return false;
    }

    @Override
    public boolean add(int index, T newElement) {
        if (newElement == null || !inAddRange(index)) {
            return false;
        } else {
            Node newNode = new Node(newElement);
            if (index == 0) {
                if (isEmpty()) {
                    add(newElement);
                    return true;        // Return because add function will num++
                } else {
                    newNode.next = firstNode;
                    firstNode.prev = newNode;
                    firstNode = newNode;
                }
            } else if (index == num) {
                lastNode.next = newNode;
                newNode.prev = lastNode;
                lastNode = newNode;
            } else {
                Node nodeCurrent = travel(index);
                nodeCurrent.prev.next = newNode;
                newNode.prev = nodeCurrent.prev;
                newNode.next = nodeCurrent;
                nodeCurrent.prev = newNode;
            }
            num++;
            return true;
        }
    }

    @Override
    public boolean addAll(T... newElements) {
        if (newElements != null) {
            if (isElementsValid(newElements)) {
                for (T element : newElements) {
                    add(element);
                }
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean contains(T element) {
        if (element != null) {
            return travel(element) != null;
        }
        return false;
    }

    @Override
    public T get(int index) {
        T data = null;
        if (inRange(index)) {
            Node nodeCurrent = travel(index);
            data = nodeCurrent.data;
        }
        return data;
    }

    @Override
    public int indexOf(T element) {
        if (element != null) {
            int index = 0;
            for (Node nodeCurrent = firstNode; nodeCurrent != null && 
                    inRange(index); index++, nodeCurrent = nodeCurrent.next) {
                if (nodeCurrent.data.equals(element)) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return num == 0;
    }

    @Override
    public boolean remove(T element) {
        if (element == null || isEmpty()) {
            return false;
        } else {
            Node nodeCurrent = travel(element);
            if (nodeCurrent != null) {
                remove(nodeCurrent);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean remove(int index) {
        if (isEmpty() || !inRange(index)) {
            return false;
        } else {
            remove(travel(index));
            return true;
        }
    }

    @Override
    public boolean removeAll(T... elements) {
        if (isEmpty() || !isElementsValid(elements)) {
            return false;
        } else {
            for (T element : elements) {
                remove(element);
            }
            return true;
        }
    }

    @Override
    public boolean set(int index, T newElement) {
        if (isEmpty() || !inRange(index) || newElement == null) {
            return false;
        } else {
            Node nodeCurrent = travel(index);
            nodeCurrent.data = newElement;
            return true;
        }
    }

    @Override
    public int sizeOf() {
        return num;
    }

    @Override
    public ListInterface where(WhereClause<T> list) {
        ListInterface<T> linkedList = new DoublyLinkedList<>();

        for (Node nodeCurrent = firstNode; nodeCurrent != null; nodeCurrent = nodeCurrent.next) {
            if (list.match(nodeCurrent.data)) {
                linkedList.add(nodeCurrent.data);
            }
        }
        return linkedList;
    }

    @Override
    public void orderBy(OrderClause<T> list) {
        int endIndex = num - 1;
        // Return true if bubble sort pass has changed
        // Return false if end index reduced by 1 and continue until next sorting
        while (bubbleSort(endIndex--, list)) {
        }
    }

    @Override
    public T firstOrDefault(FirstOrDefaultClause<T> list) {
        T data = null;
        boolean found = false;
        for (Node nodeCurrent = firstNode; nodeCurrent != null && !found; nodeCurrent = nodeCurrent.next) {
            if (list.match(nodeCurrent.data)) {
                data = nodeCurrent.data;
                
                found = true;
            }
        }
        return data;
    }

    @Override
    public Iterator<T> getIterator() {
        return new DoublyLinkListIterator();
    }

    private class Node {
        private T data;
        private Node next;
        private Node prev;

        private Node(T data) {
            this.data = data;
        }
    }
    
    private boolean bubbleSort(int endIndex, OrderClause<T> list) {
        int beginIndex = 0;
        boolean hasChanges = false;
        for (Node nodeCurrent = firstNode; beginIndex < endIndex; beginIndex++, nodeCurrent = nodeCurrent.next) {
            if (list.compare(nodeCurrent.data, nodeCurrent.next.data) == OrderClause.MOVE_BACKWARD) {
                T temp = nodeCurrent.data;
                nodeCurrent.data = nodeCurrent.next.data;
                nodeCurrent.next.data = temp;
                hasChanges = true;
            }
        }
        return hasChanges;
    }

    private void remove(Node nodeCurrent) {
        if (nodeCurrent == firstNode && nodeCurrent == lastNode) {
            firstNode = null;
            lastNode = null;
        } else if (nodeCurrent == firstNode) {
            firstNode.next.prev = null;
            firstNode = firstNode.next;
        } else if (nodeCurrent == lastNode) {
            lastNode.prev.next = null;
            lastNode = lastNode.prev;
        } else {
            nodeCurrent.prev.next = nodeCurrent.next;
            nodeCurrent.next.prev = nodeCurrent.prev;
        }
        num--;
    }

    private boolean isElementsValid(T... newElements) {
        boolean valid = true;
        for (int i = 0; i < newElements.length && valid; i++) {
            if (newElements[i] == null) {
                valid = false;
            }
        }
        return valid;
    }

    private Node travel(T element) {
        Node nodeCurrent = firstNode;
        boolean arrive = false;

        while (nodeCurrent != null && !arrive) {
            if (nodeCurrent.data.equals(element)) {
                arrive = true;
            } else {
                nodeCurrent = nodeCurrent.next;
            }
        }
        return nodeCurrent;
    }

    private Node travel(int dest) {
        int dev = num / 2;
        return dest < dev ? travelFromFirstTo(dest) : travelFromLastTo(dest);
    }

    private Node travelFromLastTo(int dest) {
        Node nodeCurrent = lastNode;
        int begin = num - 1;

        while (begin != dest) {
            nodeCurrent = nodeCurrent.prev;
            begin--;
        }
        return nodeCurrent;
    }

    private Node travelFromFirstTo(int dest) {
        Node nodeCurrent = firstNode;
        int begin = 0;

        while (begin != dest) {
            nodeCurrent = nodeCurrent.next;
            begin++;
        }
        return nodeCurrent;
    }

    private boolean inAddRange(int index) {
        return index >= 0 && index <= num;
    }

    private boolean inRange(int index) {
        return index >= 0 && index < num;
    }

    @Override
    public String toString() {
        String str = "";
        for (Node nodeCurrent = firstNode; nodeCurrent != null; nodeCurrent = nodeCurrent.next) {
            str += nodeCurrent.data + "\n";
        }
        return str;
    }

    private class DoublyLinkListIterator implements Iterator<T> {
        Node nodeCurrent = firstNode;

        @Override
        public boolean hasNext() {
            return nodeCurrent != null;
        }

        @Override
        public T next() {
            T data = null;
            if (hasNext()) {
                data = nodeCurrent.data;
                nodeCurrent = nodeCurrent.next;
            }
            return data;
        }

    }
}
