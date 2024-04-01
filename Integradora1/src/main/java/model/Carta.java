package model;

public class Carta {
    private String color;
    private int numero;
    private String tipoEspecial; // Nuevo atributo para las cartas especiales

    // Constructor para cartas normales
    public Carta(String color, int numero) {
        this.color = color;
        this.numero = numero;
        this.tipoEspecial = null; // Las cartas normales no tienen tipo especial
    }

    // Constructor para cartas especiales
    public Carta(String tipoEspecial) {
        this.color = null;
        this.numero = -1;
        this.tipoEspecial = tipoEspecial;
    }

    // Métodos getter para los atributos
    public String getColor() {
        return color;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipoEspecial() {
        return tipoEspecial;
    }

    // Método para determinar si la carta es especial
    public boolean esEspecial() {
        return tipoEspecial != null;
    }

    // Método toString para imprimir la carta
    @Override
    public String toString() {
        if (esEspecial()) {
            return tipoEspecial;
        } else {
            return color + " " + numero;
        }
    }
}

