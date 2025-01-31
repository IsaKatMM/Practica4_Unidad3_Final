package com.Practica3.rest.controller.dao;

import com.Practica3.rest.controller.dao.implement.AdapterDao;
import com.Practica3.rest.controller.tda.list.LinkedList;
import com.Practica3.rest.models.Iglesias;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;
import com.Practica3.rest.controller.tda.graph.metodos.DFS;
import com.Practica3.rest.controller.tda.graph.metodos.Bellman_Ford;
import com.Practica3.rest.controller.tda.graph.metodos.DFS;
import com.Practica3.rest.controller.tda.graph.metodos.Floyd;
import com.Practica3.rest.controller.tda.graph.Graph;

public class IglesiasDao extends AdapterDao<Iglesias> {
    private Iglesias iglesias;
    private LinkedList<Iglesias> listAll;
    private GraphLabelNoDirect<String> graph;
    private LinkedList<String> vertexName;
    private String name = "Grafo.json";

    public IglesiasDao() {
        super(Iglesias.class);
    }

    public Iglesias getIglesias() {
        if (iglesias == null) {
            iglesias = new Iglesias();
        }
        return this.iglesias;
    }

    public void setIglesias(Iglesias iglesias) {
        this.iglesias = iglesias;
    }

    public LinkedList<Iglesias> getListAll() {
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        getIglesias().setId(id);
        persist(getIglesias());
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getIglesias(), getIglesias().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        for (int i = 0; i < getListAll().getSize(); i++) {
            Iglesias pro = getListAll().get(i);
            if (pro.getId().equals(id)) {
                getListAll().delete(i);
                return true;
            }
        }
        throw new Exception("Proyecto no encontrado con ID: " + id);
    }

    ////////////////// GRAFOS/////////////////////////

    public GraphLabelNoDirect<String> creategraph() {
        if (vertexName == null) {
            vertexName = new LinkedList<>();
        }
        LinkedList<Iglesias> list = this.getListAll();
        if (!list.isEmpty()) {
            if (graph == null) {
                System.out.println("Grafo" + graph);
                graph = new GraphLabelNoDirect<>(list.getSize(), String.class);
            }

            Iglesias[] iglesias = list.toArray();
            for (int i = 0; i < iglesias.length; i++) {
                this.graph.labelsVertices(i + 1, iglesias[i].getNombre());
                System.out.println("vertices " + vertexName);

                vertexName.add(iglesias[i].getNombre());
            }
            this.graph.drawGraph();
        }
        System.out.println("Grafo creado exitosamente " + graph);
        return this.graph;
    }

    // Guardar el grafo en un archivo
    public void saveGraph() throws Exception {
        this.graph.saveGraphLabel(name);
    }

    // quiero obtener todos los datos de grafo sin que se guarde nuevo solo obtener
    // los que ya estan
    public JsonArray obtainWeights() throws Exception {
        if (graph == null) {
            creategraph();
        }

        if (graph.existsFile(name)) {
            graph.cargarModelosDesdeDao();
            graph.loadGraph(name);
            JsonArray graphData = graph.obtainWeights();
            System.out.println("Modelo vis.js " + graphData);
            return graphData;
        } else {
            throw new Exception("El archivo de grafo no existe.");
        }
    }

    public JsonObject getGraphData() throws Exception {
        if (graph == null) {
            creategraph();
        }

        if (graph.existsFile(name)) {
            graph.cargarModelosDesdeDao();
            graph.loadGraph(name);
            JsonObject graphData = graph.getVisGraphData();
            System.out.println("Modelo de vis,js " + graphData);
            return graphData;
        } else {
            throw new Exception("El archivo de grafo no existe.");
        }
    }

    public GraphLabelNoDirect<String> crearuniosnes() throws Exception {
        if (graph == null) {
            creategraph();
        }
        if (graph.existsFile(name)) {
            graph.cargarModelosDesdeDao();
            graph.loadGraphWithRandomEdges(name);
            System.out.println("Modelo asociado al grafo: " + name);
        } else {
            throw new Exception("El archivo de grafo no existe.");
        }
        saveGraph();
        return graph;
    }



    public String dfs(int origen) throws Exception {
        if (graph == null) {
            throw new Exception("El grafo no existe");
        }
        DFS dfsAlgoritmo = new DFS(graph, origen);
        // recorrido
        String recorrido = dfsAlgoritmo.recorrerGrafo();
        return recorrido;
    }

    public String caminoCorto(int origen, int destino, int algoritmo) throws Exception {
        if (graph == null) {
            throw new Exception("Grafo no existe");
        }

        System.out.println("Calculando camino corto desde " + origen + " hasta " + destino);

        String camino = "";

        if (algoritmo == 1) { // Usar algoritmo de Floyd
            Floyd floydWarshall = new Floyd(graph, origen, destino);
            camino = floydWarshall.encontrarCaminoMasCorto();
        } else {
            Bellman_Ford bellmanFord = new Bellman_Ford(graph, origen, destino);
            camino = bellmanFord.encontrarCaminoMasCorto();
        }

        System.out.println("Camino corto calculado: " + camino);
        return camino; 

    }

}
