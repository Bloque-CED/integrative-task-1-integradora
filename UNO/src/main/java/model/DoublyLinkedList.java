package model;

import exception.NodeNotFoundException;

import java.util.function.Function;

public class DoublyLinkedList<T extends Comparable<T>> {

    class Node<T> {
        private T data;
        private Node<T> nextNode;
        private Node<T> prevNode;

        Node(T data){
            this.data = data;
            this.nextNode = null;
            this.prevNode = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }

        public Node<T> getPrevNode() {
            return prevNode;
        }

        public void setPrevNode(Node<T> prevNode) {
            this.prevNode = prevNode;
        }
    }

    public Node<T> head;
    public Node<T> tail;
    public int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addNode(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNextNode(newNode);
            newNode.setPrevNode(tail);
            tail = newNode;
        }
        size++;
    }

    public Node<T> searchNode(Function<T, ?> mapper, Object target) throws NodeNotFoundException{
        if (head == null) {
            throw new NodeNotFoundException("This list is empty!");
        }
        Node<T> node = head;
        while (node != null) {
            Object mappedValue = mapper.apply(node.getData());
            if (mappedValue != null && mappedValue.equals(target)) {
                return node;
            }
            node = node.getNextNode();
        }
        throw new NodeNotFoundException("Node couldn't be found, sorry :(");
    }

    public String removeNode(Function<T, ?> mapper, Object target) {
        try {
            Node<T> nodeDelete = searchNode(mapper, target);
            if (nodeDelete != null) {
                if (nodeDelete.equals(head)) {
                    head = head.getNextNode();
                    if (head != null)
                        head.setPrevNode(null);
                } else if (nodeDelete.equals(tail)) {
                    tail = tail.getPrevNode();
                    if (tail != null)
                        tail.setNextNode(null);
                } else {
                    nodeDelete.getPrevNode().setNextNode(nodeDelete.getNextNode());
                    nodeDelete.getNextNode().setPrevNode(nodeDelete.getPrevNode());
                }
                size--;
                return ("Successfully deleted!");
            }
        } catch (NodeNotFoundException e) {
            return ("Error: "+e.getMessage());
        }
        return null;
    }
    public void addNodeInOrder(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null || data.compareTo(head.getData()) <= 0) {
            newNode.setNextNode(head);
            if (head != null) {
                head.setPrevNode(newNode);
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node<T> current = head;
            while (current.getNextNode() != null && data.compareTo(current.getNextNode().getData()) > 0) {
                current = current.getNextNode();
            }
            newNode.setNextNode(current.getNextNode());
            if (current.getNextNode() != null) {
                current.getNextNode().setPrevNode(newNode);
            } else {
                tail = newNode;
            }
            current.setNextNode(newNode);
            newNode.setPrevNode(current);
        }
        size++;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}
