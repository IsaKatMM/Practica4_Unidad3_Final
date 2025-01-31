package com.Practica3.rest;


import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.Practica3.rest.controller.dao.services.IglesiasServices;
import com.Practica3.rest.controller.tda.list.LinkedList;
import com.Practica3.rest.models.Iglesias;
import com.Practica3.rest.controller.dao.IglesiasDao;
import com.Practica3.rest.controller.tda.list.Exception.ListEmptyException;
import com.Practica3.rest.controller.tda.graph.Graph;
import com.Practica3.rest.controller.tda.graph.metodos.Bellman_Ford;
import com.Practica3.rest.controller.tda.graph.metodos.Floyd;
import com.Practica3.rest.controller.tda.graph.metodos.DFS;
import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    private GraphLabelNoDirect<String> graph;

      /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
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
    
            // Convertir el tiempo a milisegundos (opcional)
            double tiempoEjecucionMs = tiempoEjecucion / 1_000_000.0;
    
            // Mostrar el tiempo de ejecución
            System.out.println("Tiempo de ejecución de Bellman-Ford: " + tiempoEjecucion + " ns (" + tiempoEjecucionMs + " ms)");
        }
    
        System.out.println("Camino corto calculado: " + camino);
        return camino; // Regresar el camino calculado
    }
    
}
