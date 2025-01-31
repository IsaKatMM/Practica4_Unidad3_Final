package com.Practica3.rest.controller.tda.graph.metodos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;

public class DFS {
    private GraphLabelNoDirect<String> grafo;
    private int origen;

    public DFS(GraphLabelNoDirect<String> grafo, int origen) {
        this.grafo = grafo;
        this.origen = origen;
    }

    public String recorrerGrafo() throws Exception {
        int n = grafo.nro_vertices();
        boolean[] visitados = new boolean[n + 1]; 
        Arrays.fill(visitados, false);
        Stack<Integer> pila = new Stack<>();
        pila.push(origen);
        visitados[origen] = true;

        // Lista para almacenar el recorrido
        List<Integer> recorrido = new ArrayList<>();

        // DFS
        while (!pila.isEmpty()) {
            int nodoActual = pila.pop();
            recorrido.add(nodoActual);

            List<Integer> vecinos = obtenerVecinos(nodoActual);
            for (int vecino : vecinos) {
                if (!visitados[vecino]) {
                    visitados[vecino] = true;
                    pila.push(vecino);
                }
            }
        }
        return "Recorrido DFS: " + recorrido.toString();
    }

    private List<Integer> obtenerVecinos(int nodo) throws Exception {
        List<Integer> vecinos = new ArrayList<>();
        int n = grafo.nro_vertices();
        for (int i = 1; i <= n; i++) {
            try {

                Float peso = grafo.getWeigth2(nodo, i);
                if (peso != null && peso > 0) {
                    vecinos.add(i); 
                }
            } catch (Exception e) {
            }
        }

        return vecinos;
    }
}
