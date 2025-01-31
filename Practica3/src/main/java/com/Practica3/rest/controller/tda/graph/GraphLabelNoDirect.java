package com.Practica3.rest.controller.tda.graph;

import com.Practica3.rest.controller.tda.list.Exception.LabelException;

import java.util.Random;

import com.Practica3.rest.controller.tda.graph.GraphLabelDirect;
import com.Practica3.rest.controller.tda.graph.Adyacencia;

public class GraphLabelNoDirect<E> extends GraphLabelDirect<E> {
    public GraphLabelNoDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices, clazz);
    }

    public void insertEdgeL(E v1, E v2, Float wiegth) throws Exception {
        if (isLabelsGraph()) {
            add_edge(getVerticeL(v1), getVerticeL(v2), wiegth);
            add_edge(getVerticeL(v2), getVerticeL(v1), wiegth);

        } else {
            throw new Exception("Grafo no etiquetado");
        }
    }

    public Float getWeigth2(Integer v1, Integer v2) throws Exception {
        return wieght_edge(v1, v2);
    }

    
}