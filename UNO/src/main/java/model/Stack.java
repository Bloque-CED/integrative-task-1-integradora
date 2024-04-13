package model;

import exception.NodeNotFoundException;

import java.util.EmptyStackException;
import java.util.function.Function;


public class Stack<T> implements IStack<T> {
    public LinkedListStructure<T> list;

    public Stack() {
        this.list = new LinkedListStructure<>();
    }
    @Override
    public void push(T element) {
        list.addNode(element);
    }
    @Override
    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T popped = peek();
        // Función para identificar el último nodo
        Function<T, Boolean> isLastNode = data -> data.equals(popped);
        // Eliminar el último nodo
        String result = list.removeNode(isLastNode);
        if (result.startsWith("Error")) {
            throw new NodeNotFoundException(result);
        }
        return popped;
    }





    @Override
    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        // Comenzamos desde el nodo inicial (nodeHead)
        LinkedListStructure<T>.Node<T> currentNode = list.nodeHead;
        // Recorremos la lista hasta llegar al último nodo
        while (currentNode.getNextNode() != null) {
            currentNode = currentNode.getNextNode();
        }
        // Devolvemos el dato del último nodo (nodo en la parte superior de la pila)
        return currentNode.getData();
    }




    @Override
    public boolean isEmpty() {
        return list.getSize() == 0;
    }

    public int size() {
        return list.getSize();
    }

    public void clear() {
        list = new LinkedListStructure<>();
    }

    public LinkedListStructure<T> getList() {
        return list;
    }

    public void setList(LinkedListStructure<T> list) {
        this.list = list;
    }
}