package com.Practica3.rest.controller.medicion;

import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;
import com.Practica3.rest.controller.tda.graph.metodos.Bellman_Ford;
import com.Practica3.rest.controller.tda.graph.metodos.Floyd;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class bellman {

    private GraphLabelNoDirect<String> graph;

    // Constructor para inicializar el grafo
    public bellman(GraphLabelNoDirect<String> graph) {
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

    // Método para calcular el camino más corto
    public String calcularCaminoCorto(int origen, int destino, int algoritmo) throws Exception {
        if (graph == null) {
            throw new Exception("Grafo no existe");
        }

        System.out.println("Calculando camino corto desde " + origen + " hasta " + destino);

        String camino = "";

        if (algoritmo == 1) { // Usar algoritmo de Floyd
            Floyd floydWarshall = new Floyd(graph, origen, destino);
            camino = floydWarshall.encontrarCaminoMasCorto();
        } else { // Usar algoritmo de Bellman-Ford
            // Medir el tiempo de inicio
            long inicio = System.nanoTime();

            // Ejecutar el algoritmo de Bellman-Ford
            Bellman_Ford bellmanFord = new Bellman_Ford(graph, origen, destino);
            camino = bellmanFord.encontrarCaminoMasCorto();

            // Medir el tiempo de finalización
            long fin = System.nanoTime();

            // Calcular el tiempo de ejecución en nanosegundos
            long tiempoEjecucion = fin - inicio;

            // Convertir el tiempo a milisegundos usando TimeUnit
            long tiempoEjecucionMs = TimeUnit.NANOSECONDS.toMillis(tiempoEjecucion);

            // Mostrar el tiempo de ejecución
            System.out.println("Tiempo de ejecución de Bellman-Ford: " + tiempoEjecucion + " ns (" + tiempoEjecucionMs + " ms)");
        }

        System.out.println("Camino corto calculado: " + camino);
        return camino; // Regresar el camino calculado
    }

    // Método principal para probar la funcionalidad y medir tiempos
    public static void main(String[] args) {
        try {
            // Crear una instancia de BellmanMedicion
            bellman bellman = new bellman(null);

            // Array de tamaños de grafo
            int[] tamanios = {10, 20, 30};

            for (int size : tamanios) {
                int numAristas = size * 2; // Ajustar el número de aristas según el tamaño

                // Generar grafo aleatorio
                bellman.generarGrafoAleatorio(size, numAristas);

                // Calcular el camino más corto usando Bellman-Ford
                int origen = 1; // Vértice de origen
                int destino = size; // Último vértice como destino
                System.out.println("Probando con " + size + " vértices y " + numAristas + " aristas:");
                String resultado = bellman.calcularCaminoCorto(origen, destino, 2); // 2 para Bellman-Ford

                // Imprimir el resultado
                System.out.println(resultado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*private GraphLabelNoDirect<String> graph;

    // Constructor para inicializar el grafo
    public bellman(GraphLabelNoDirect<String> graph) {
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

    // Método para calcular el camino más corto
    public String caminoCorto(int origen, int destino, int algoritmo) throws Exception {
        if (graph == null) {
            throw new Exception("Grafo no existe");
        }

        System.out.println("Calculando camino corto desde " + origen + " hasta " + destino);

        String camino = "";

        if (algoritmo == 1) { // Usar algoritmo de Floyd
            Floyd floydWarshall = new Floyd(graph, origen, destino);
            camino = floydWarshall.encontrarCaminoMasCorto();
        } else { // Usar algoritmo de Bellman-Ford
            // Medir el tiempo de inicio
            long inicio = System.nanoTime();

            // Ejecutar el algoritmo de Bellman-Ford
            Bellman_Ford bellmanFord = new Bellman_Ford(graph, origen, destino);
            camino = bellmanFord.encontrarCaminoMasCorto();

            // Medir el tiempo de finalización
            long fin = System.nanoTime();

            // Calcular el tiempo de ejecución en nanosegundos
            long tiempoEjecucion = fin - inicio;

            // Convertir el tiempo a milisegundos usando TimeUnit
            long tiempoEjecucionMs = TimeUnit.NANOSECONDS.toMillis(tiempoEjecucion);

            // Mostrar el tiempo de ejecución
            System.out.println("Tiempo de ejecución de Bellman-Ford: " + tiempoEjecucion + " ns (" + tiempoEjecucionMs + " ms)");
        }

        System.out.println("Camino corto calculado: " + camino);
        return camino; // Regresar el camino calculado
    }

    // Método principal para probar la funcionalidad
    public static void main(String[] args) {
        try {
            // Crear una instancia de Bellman
            bellman bellman = new bellman(null);

            // Generar un grafo aleatorio con 10 vértices y 20 aristas
            bellman.generarGrafoAleatorio(10, 20);

            // Calcular el camino más corto usando Bellman-Ford
            int origen = 1; // Vértice de origen
            int destino = 10; // Vértice de destino
            String resultado = bellman.caminoCorto(origen, destino, 2); // 2 para Bellman-Ford

            // Imprimir el resultado
            System.out.println(resultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}