package com.Practica3.rest.controller.tda.graph.metodos;

import java.util.HashMap;
import java.util.Map;

import com.Practica3.rest.controller.tda.graph.Adyacencia;
import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;
import com.Practica3.rest.controller.tda.list.LinkedList;

public class Bellman_Ford {
    private GraphLabelNoDirect<String> grafo;
    private int origen;
    private int destino;
    private Map<Integer, Float> distancias;
    private Map<Integer, Integer> predecesores;

    public Bellman_Ford(GraphLabelNoDirect<String> grafo, int origen, int destino) {
        this.grafo = grafo;
        this.origen = origen;
        this.destino = destino;
        this.distancias = new HashMap<>();
        this.predecesores = new HashMap<>();
    }

    public String encontrarCaminoMasCorto() throws Exception {
        int n = grafo.nro_vertices();
        inicializarEstructuras(n);
        boolean cambio;
        for (int i = 1; i < n; i++) {
            cambio = relajarAristas();
            if (!cambio) {
                break; 
            }
        }
        if (verificarCiclosNegativos()) {
            return "El grafo tiene un ciclo negativo";
        }

        return reconstruirCamino();
    }

    private void inicializarEstructuras(int n) {
        for (int i = 1; i <= n; i++) {
            distancias.put(i, Float.MAX_VALUE);
            predecesores.put(i, -1);
        }
        distancias.put(origen, 0f);
    }

    private boolean relajarAristas() throws Exception {
        boolean cambio = false;
        for (int u = 1; u <= grafo.nro_vertices(); u++) {
            LinkedList<Adyacencia> adyacencias = grafo.adyacencias(u);
            for (int j = 0; j < adyacencias.getSize(); j++) {
                Adyacencia adyacencia = adyacencias.get(j);
                int v = adyacencia.getdestination();
                float peso = adyacencia.getweight();

                if (distancias.get(u) != Float.MAX_VALUE && distancias.get(u) + peso < distancias.get(v)) {
                    distancias.put(v, distancias.get(u) + peso);
                    predecesores.put(v, u);
                    cambio = true;
                }
            }
        }
        return cambio;
    }

    private boolean verificarCiclosNegativos() throws Exception {
        for (int u = 1; u <= grafo.nro_vertices(); u++) {
            LinkedList<Adyacencia> adyacencias = grafo.adyacencias(u);
            for (int j = 0; j < adyacencias.getSize(); j++) {
                Adyacencia adyacencia = adyacencias.get(j);
                int v = adyacencia.getdestination();
                float peso = adyacencia.getweight();

                if (distancias.get(u) != Float.MAX_VALUE && distancias.get(u) + peso < distancias.get(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String reconstruirCamino() throws Exception {
        if (distancias.get(destino) == Float.MAX_VALUE) {
            return "No hay camino";
        }

        StringBuilder camino = new StringBuilder();
        int actual = destino;
        float distanciaTotal = 0;

        while (actual != -1) {
            if (predecesores.get(actual) != -1) {
                distanciaTotal += grafo.getWeigth2(predecesores.get(actual), actual);
            }
            camino.insert(0, actual + " - ");
            actual = predecesores.get(actual);
        }
        camino.delete(camino.length() - 4, camino.length());

        return "Camino: " + camino.toString() + "\nDistancia: " + distanciaTotal + " km";
    }
}