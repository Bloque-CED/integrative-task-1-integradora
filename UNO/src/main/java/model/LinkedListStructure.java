package model;
import exception.NodeNotFoundException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedListStructure<T> {
    class Node<T> {
        private T data;
        private Node<T> nextNode;

        Node(T data){
            this.data=data;
            this.nextNode=null;
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
    }
    public Node<T> nodeHead;
    public int nodeCounter;

    public LinkedListStructure() {
        this.nodeHead = null;
        this.nodeCounter = 0;
    }

    public void addNode(T data) {
        if (nodeHead == null) {
            nodeHead = new Node<>(data);
        } else {
            Node<T> currentNode = nodeHead;
            while (currentNode.getNextNode() != null) {
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(new Node<>(data));
        }
        nodeCounter++;
    }


    public Node<T> searchNode(Function<T, ?> mapper, Object target) throws NodeNotFoundException{
        if (nodeHead == null) {
            throw new NodeNotFoundException("This list is empty!");
        }
        Node<T> node = nodeHead;
        while (node != null) {
            Object mappedValue = mapper.apply(node.getData());
            if (mappedValue != null && mappedValue.equals(target)) {
                return node;
            }
            node = node.getNextNode();
        }
        throw new NodeNotFoundException("Node couldn't be found, sorry :(");
    }
    public Node<T> searchNode(Predicate<T> predicate) throws NodeNotFoundException {
        if (nodeHead == null) {
            throw new NodeNotFoundException("This list is empty!");
        }
        Node<T> node = nodeHead;
        while (node != null) {
            if (predicate.test(node.getData())) {
                return node;
            }
            node = node.getNextNode();
        }
        throw new NodeNotFoundException("Node couldn't be found, sorry :(");
    }

    public String removeNode(Function<T, Boolean> matcher) {
        Node<T> current = nodeHead;
        Node<T> previous = null;
        while (current != null) {
            if (matcher.apply(current.getData())) {
                if (previous == null) {
                    nodeHead = current.getNextNode();
                } else {
                    previous.setNextNode(current.getNextNode());
                }
                nodeCounter--;
                return "Successfully deleted!";
            }
            previous = current;
            current = current.getNextNode();
        }
        return "Error: Node not found";
    }


    public String modifyDataNode(Function<T, ?> mapper, Object target, Consumer<T> modifier) {
        try {
            Node<T> modifyNode = searchNode(mapper, target);
            if (modifyNode != null) {
                T data = modifyNode.getData();
                modifier.accept(data);
                return ("Successfully modified!");
            }
        } catch (NodeNotFoundException e) {
            return ("Error: " + e.getMessage());
        }
        return "Error: Node not found";
    }
    public int getSize() {
        return nodeCounter;
    }





}
