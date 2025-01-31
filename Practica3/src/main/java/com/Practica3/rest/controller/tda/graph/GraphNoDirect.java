package com.Practica3.rest.controller.tda.graph;

import com.Practica3.rest.controller.tda.list.Exception.ListEmptyException;

// este grafo es bidireccional
public class GraphNoDirect extends GraphDirect {
    public GraphNoDirect(Integer nro_vertices) {
        super(nro_vertices); // herecncia de graphdirect
    }

    @Override
     public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_vertices() && v2.intValue() <= nro_vertices()) {
            if (!is_edge(v1, v2)) {
                
                setNro_edges(nro_edges() +1);
                Adyacencia aux = new Adyacencia(v2, weight);
                aux.setweight(weight);
                aux.setdestination(v2);
                getListAdyacencias()[v1].add(aux);

                Adyacencia aux2 = new Adyacencia(v1, weight);
                aux2.setweight(weight);
                aux2.setdestination(v1);
                getListAdyacencias()[v2].add(aux2);
            }
        } else {
            throw new  ListEmptyException("El vertice no existe");
        }
    }   
}
