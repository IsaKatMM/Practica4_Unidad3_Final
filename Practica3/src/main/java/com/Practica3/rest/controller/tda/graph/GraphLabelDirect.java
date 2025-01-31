package com.Practica3.rest.controller.tda.graph;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;
import com.Practica3.rest.controller.tda.list.LinkedList;
import com.Practica3.rest.controller.tda.list.Exception.LabelException;
import com.Practica3.rest.controller.tda.graph.Adyacencia;

public class GraphLabelDirect<E> extends GraphDirect{
    protected E labels [];
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz; 

    public GraphLabelDirect (Integer nro_vertices, Class<E> clazz){
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices +1);
        dictVertices = new HashMap<>();
    }

    public Integer nro_vertices() {
        return super.nro_vertices();
    }

    public Boolean is_edgeL (E v1, E v2) throws Exception{
        if(isLabelsGraph()){
            return is_edge(getVerticeL(v1), getVerticeL(v2) );
        } else {
            throw new LabelException("Grafo no etiquetado");
        }

    }

    public void insertEdgeL(E v1, E v2, Float wiegth) throws Exception{
        if(isLabelsGraph()){
            add_edge(getVerticeL(v1), getVerticeL(v2), wiegth);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeL(E v1, E v2)throws Exception{
        if(isLabelsGraph()){
            insertEdgeL(v1, v2, Float.NaN);
            //add_edge(getVerticeL(v1), getVerticeL(v2), Float.NaN);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public LinkedList<Adyacencia> adyacenciasL(E v) throws Exception{
        if(isLabelsGraph()){
            return adyacencias(getVerticeL(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }



    public void labelsVertices(Integer v, E data){
        labels[v] = data;
        dictVertices.put(data, v); //para ver si los labels estan etiquetados o no, antesde hacer lo demas se debe saber si estan etiquetados o no
    }
    
    // operaciones para ver si esta Etiquetados

    //si esta etiquetado, los grafos tienen qu estar etiquetados
    public Boolean isLabelsGraph(){
        Boolean band = true;
        for(int i= 1; i< labels.length; i++){
            if(labels [i] == null){
                band = false;
                break;
            }
        }
        return band;
    }

    

    public Integer getVerticeL (E label){
        return dictVertices.get(label); 
    }

    public E getLabelL (Integer v1){
        return labels[v1];
    }

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder();
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo.append("V").append(i).append(" [")
                        .append(getLabelL(i).toString()).append("]")
                        .append("\n");
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for (Adyacencia a : ady) {
                        grafo.append("ady V").append(a.getdestination())
                                .append(" weight: ").append(a.getweight()).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            grafo.append("Error: ").append(e.getMessage());
        }
        return grafo.toString();
    }

    public String drawGraph() {
        StringBuilder grafo = new StringBuilder();

        grafo.append("digraph G {\n");
        try {
            grafo.append("digraph G {\n");
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo.append("V").append(i).append(" [")
                        .append("label=\"").append(getLabelL(i).toString()).append("\"]")
                        .append("\n");
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for (Adyacencia a : ady) {
                        grafo.append("V").append(i).append(" -> V").append(a.getdestination())
                                .append(" [label=\"").append(a.getweight()).append("\"]\n");
                    }
                }
            }
            grafo.append("}");
        } catch (Exception e) {
            grafo.append("Error: ").append(e.getMessage());
        }
        return grafo.toString();
    }
}
