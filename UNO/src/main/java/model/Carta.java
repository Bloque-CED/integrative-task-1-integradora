package model;

public class Carta {
    private String color;
    private String valor;
    private int id;

    public Carta(String color, String valor, int id) {
        this.color = color;
        this.valor = valor;
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public String getValor() {
        return valor;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return color + " " + valor;
    }

    public boolean coincide(Carta otra) {
        return this.color.equals(otra.color) || this.valor.equals(otra.valor) || this.color.equals("Negro");
    }
}

