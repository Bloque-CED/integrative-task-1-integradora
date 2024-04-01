package model;

public class Carta {
    private String color;
    private int numero;
    private String tipo;

    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
        this.tipo = null; // Las cartas normales no tienen tipo
    }

    public Carta(String tipo) {
        this.color = null;
        this.numero = -1;
        this.tipo = tipo; // Las cartas especiales tienen tipo
    }

    public String getColor() {
        return color;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        if (tipo != null) {
            return tipo;
        } else {
            return color + " " + numero;
        }
    }
}

