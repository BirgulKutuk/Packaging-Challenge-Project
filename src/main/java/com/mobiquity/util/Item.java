package com.mobiquity.util;

public class Item {

    private int indexNumber;
    private double weight;
    private double cost;

    public Item(int indexNumber, double weight, double cost) {
        this.indexNumber = indexNumber;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Item{" +
                "indexNumber=" + indexNumber +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
