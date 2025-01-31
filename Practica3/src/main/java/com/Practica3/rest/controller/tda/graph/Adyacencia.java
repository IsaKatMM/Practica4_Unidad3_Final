package com.Practica3.rest.controller.tda.graph;

import java.util.Random;

public class Adyacencia {
    private Integer destination;
    private Float weight;  //peso

    public Adyacencia(Integer destination, Float weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Adyacencia(){} //constructor por defecto



    public Integer getdestination() {
        return this.destination;
    }

    public void setdestination(Integer destination) {
        this.destination = destination;
    }

    public Float getweight() {
        return this.weight;
    }

    public void setweight(Float weight) {
        this.weight = weight;
    }

    
    @Override
    public String toString() {
        return "Adyacencia{" +
                "destination=" + destination +
                ", weight=" + weight +
                '}';
    }
}
