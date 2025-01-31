package com.Practica3.rest.controller.medicion;

import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;
import com.Practica3.rest.controller.tda.graph.metodos.Floyd;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class floy {
    private GraphLabelNoDirect<String> graph;

    // Constructor para inicializar el grafo
    public floy(GraphLabelNoDirect<String> graph) {
        this.graph = graph;
    }

    // Método para generar un grafo aleatorio
    public void generarGrafoAleatorio(int numVertices, int numAristas) {
        Random random = new Random();
        graph = new GraphLabelNoDirect<>(numVertices, String.class);

        // Añadir vértices
        for (int i = 1; i <= numVertices; i++) {
            graph.labelsVertices(i, "V" + i);
        }

        // Añadir aristas con pesos aleatorios
        for (int i = 0; i < numAristas; i++) {
            int origen = random.nextInt(numVertices) + 1;
            int destino = random.nextInt(numVertices) + 1;
            float peso = random.nextFloat() * 100; // Peso aleatorio entre 0 y 100
            try {
                graph.add_edge(origen, destino, peso);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para calcular el camino más corto usando Floyd-Warshall
    public String calcularCaminoCorto(int origen, int destino) throws Exception {
        if (graph == null) {
            throw new Exception("Grafo no existe");
        }

        System.out.println("Calculando camino corto desde " + origen + " hasta " + destino);

        // Medir el tiempo de inicio
        long inicio = System.nanoTime();

        // Ejecutar el algoritmo de Floyd-Warshall
        Floyd floydWarshall = new Floyd(graph, origen, destino);
        String camino = floydWarshall.encontrarCaminoMasCorto();

        // Medir el tiempo de finalización
        long fin = System.nanoTime();

        // Calcular el tiempo de ejecución en nanosegundos
        long tiempoEjecucion = fin - inicio;

        // Convertir el tiempo a milisegundos usando TimeUnit
        long tiempoEjecucionMs = TimeUnit.NANOSECONDS.toMillis(tiempoEjecucion);

        // Mostrar el tiempo de ejecución
        System.out.println("Tiempo de ejecución de Floyd-Warshall: " + tiempoEjecucion + " ns (" + tiempoEjecucionMs + " ms)");

        return camino; // Regresar el camino calculado
    }

    // Método principal para probar la funcionalidad y medir tiempos
    public static void main(String[] args) {
        try {
            // Crear una instancia de FloydMedicion
        floy floy= new floy(null);

            // Array de tamaños de grafo
            int[] tamanios = {10, 20, 30};

            for (int size : tamanios) {
                int numAristas = size * 2; // Ajustar el número de aristas según el tamaño

                // Generar grafo aleatorio
                floy.generarGrafoAleatorio(size, numAristas);

                // Calcular el camino más corto usando Floyd-Warshall
                int origen = 1; // Vértice de origen
                int destino = size; // Último vértice como destino
                System.out.println("Probando con " + size + " vértices y " + numAristas + " aristas:");
                String resultado = floy.calcularCaminoCorto(origen, destino);

                // Imprimir el resultado
                System.out.println(resultado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
