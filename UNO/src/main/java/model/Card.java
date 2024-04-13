package model;

public class Card {
    private String color;
    private String value;
    private String type; // Normal or Special
    private int id;

    public Card(int id, String color, String value, String type) {
        this.id = id;
        this.color = color;
        this.value = value;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean matches(Card otherCard) {
        return this.color.equals(otherCard.getColor()) ||
                this.value.equals(otherCard.getValue()) ||
                this.type.equals(otherCard.getType());
    }
}
