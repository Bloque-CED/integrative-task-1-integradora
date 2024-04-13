package model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

public class StackTest<T>{

    private Stack<Integer> stack;



    void setUp() {
        stack = new Stack<>();
        // Agregar algunos elementos a la pila para las pruebas
        stack.push(1);
        stack.push(2);
        stack.push(3);
    }
    private void setUp2() {
        stack = new Stack<>();
    }

    @Test
    void testPush() {
        setUp();

        // Verificar el tamaño de la pila después de insertar elementos
        Assertions.assertEquals(3, stack.size());

        // Verificar el elemento superior de la pila después de insertar elementos
        Assertions.assertEquals(3, stack.peek());

        // Verificar que la pila no esté vacía después de insertar elementos
        Assertions.assertFalse(stack.isEmpty());
    }

    @Test
    void testPop() {
        setUp();

        Assertions.assertEquals(3, stack.size());
        // Comprobar que el método pop devuelve el último elemento añadido
        Assertions.assertEquals(3, stack.pop());

        // Comprobar que el último elemento ha sido eliminado
        Assertions.assertEquals(2, stack.peek());
        Assertions.assertEquals(2, stack.size());
        // Comprobar que la pila no está vacía
        Assertions.assertFalse(stack.isEmpty());
    }
    @Test
    void testPopEmptyStack() {
        // Vaciar la pila
        stack.clear();

        // Verificar que la pila esté vacía
        Assertions.assertTrue(stack.isEmpty());

        // Intentar realizar pop en una pila vacía y verificar si se lanza EmptyStackException
        Assertions.assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        });
    }
    @Test
    void testPeek() {
        setUp();
        // Verificar que el tamaño de la pila sea el esperado
        Assertions.assertEquals(3, stack.size());

        // Obtener el elemento superior de la pila sin eliminarlo
        int topElement = stack.peek();

        // Verificar que el elemento superior sea el esperado
        Assertions.assertEquals(3, topElement);

        // Verificar que el tamaño de la pila no cambió
        Assertions.assertEquals(3, stack.size());
    }
    @Test
    void testPeekEmptyStack() {
        setUp();
        // Vaciar la pila
        stack.clear();

        // Verificar que la pila esté vacía
        Assertions.assertTrue(stack.isEmpty());

        Assertions.assertThrows(EmptyStackException.class, () -> {
            stack.peek();
        });
    }
    @Test
    void testIsEmpty() {
        setUp2();
        Assertions.assertTrue(stack.isEmpty());
        stack.push(1);
        Assertions.assertFalse(stack.isEmpty());
    }



    @Test
    void testSize() {
        setUp2();

        Assertions.assertEquals(0, stack.size());
        stack.push(1);
        stack.push(2);
        Assertions.assertEquals(2, stack.size());
        stack.pop();
        Assertions.assertEquals(1, stack.size());
    }

    @Test
    void testClear() {
        setUp();

        stack.clear();
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertEquals(0, stack.size());
    }

}