package com.Practica3.rest.controller.tda.graph.metodos;

import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;

public class Floyd{
    private static final float INFINITY = Float.MAX_VALUE;
    private static final int NO_PATH = -1;
    private GraphLabelNoDirect<String> grafo;
    private int origen;
    private int destino;
    private float[][] distancias;
    private int[][] siguiente;

    public Floyd(GraphLabelNoDirect<String> grafo, int origen, int destino) {
        this.grafo = grafo;
        this.origen = origen;
        this.destino = destino;
        int n = grafo.nro_vertices();
        this.distancias = new float[n][n];
        this.siguiente = new int[n][n];
    }

    public String encontrarCaminoMasCorto() throws Exception {
        int n = grafo.nro_vertices();

        // Inicialización de matrices
        inicializarMatrices(n);

        // Algoritmo de Floyd
        ejecutarFloydWarshall(n);

        // Reconstruir y devolver el camino
        return reconstruirPath();
    }

    private void inicializarMatrices(int n) throws Exception {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                    siguiente[i][j] = NO_PATH;
                } else {
                    try {
                        float peso = grafo.getWeigth2(i + 1, j + 1); // Los índices comienzan desde 1
                        if (Float.isNaN(peso) || peso <= 0) {
                            distancias[i][j] = INFINITY;
                            siguiente[i][j] = NO_PATH;
                        } else {
                            distancias[i][j] = peso;
                            siguiente[i][j] = j;
                        }
                    } catch (Exception e) {
                        distancias[i][j] = INFINITY;
                        siguiente[i][j] = NO_PATH;
                    }
                }
            }
        }
    }

    private void ejecutarFloydWarshall(int n) {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (distancias[i][k] == INFINITY) continue; // Evitar cálculos innecesarios
                for (int j = 0; j < n; j++) {
                    if (distancias[k][j] == INFINITY) continue; // Evitar cálculos innecesarios
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        siguiente[i][j] = siguiente[i][k];
                    }
                }
            }
        }
    }

    private String reconstruirPath() {
        if (siguiente[origen - 1][destino - 1] == NO_PATH) {
            return "No hay camino";
        }

        StringBuilder camino = new StringBuilder();
        int actual = origen - 1; // Ajustar índices
        float distanciaTotal = 0;

        while (actual != destino - 1) {
            camino.append(actual + 1).append(" - "); // Ajustar índices
            int siguienteNodo = siguiente[actual][destino - 1];
            distanciaTotal += distancias[actual][siguienteNodo];
            actual = siguienteNodo;
        }
        camino.append(destino); // Agregar el destino
        distanciaTotal += distancias[actual][destino - 1]; // Sumar la última distancia

        return "Camino: " + camino.toString() 
                + "\nDistancia total: " + distanciaTotal;
    }
}