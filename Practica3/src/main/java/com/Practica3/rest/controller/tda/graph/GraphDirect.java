package com.Practica3.rest.controller.tda.graph;

import com.Practica3.rest.controller.tda.list.LinkedList;
import com.Practica3.rest.controller.tda.list.Exception.ListEmptyException;
import com.Practica3.rest.controller.tda.list.Exception.OverFlowException;
import com.Practica3.rest.controller.tda.graph.Graph;
import com.Practica3.rest.controller.tda.graph.Adyacencia;

public class GraphDirect extends Graph {
    private Integer nro_vertices;
    private Integer nro_edges;
    private LinkedList<Adyacencia> listAdyacencias[]; // lista d adyacencias como arreglo se hace esto por:

    public GraphDirect(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        this.nro_edges = 0;
        listAdyacencias = new LinkedList[nro_vertices + 1];
        for (int i = 1; i <= nro_vertices; i++) {
            listAdyacencias[i] = new LinkedList<Adyacencia>();
        }
    }

    public Integer nro_edges() {
        return this.nro_edges;
    }

    public Integer nro_vertices() {
        return this.nro_vertices;
    }

    @Override
    public Boolean is_edge(Integer v1, Integer v2) throws Exception {
        Boolean is = false;
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getdestination().intValue() == v2.intValue()) {
                        is = true;
                        break;
                    }
                }
            }
        } else {
            throw new ListEmptyException("El vertice no existe");
        }
        return is;

    }

    public Float wieght_edge(Integer v1, Integer v2) throws Exception {
        Float weight = Float.NaN;
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getdestination().intValue() == v2.intValue()) {
                        weight = aux.getweight();

                        break;
                    }
                }
            }
        } else {
            throw new ListEmptyException("El vertice no existe");
        }
        System.out.println(weight);
        return weight;

    }

    // insertar con peso
    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            if (!is_edge(v1, v2)) {
                nro_edges++;
                Adyacencia aux = new Adyacencia(v2, weight);
                aux.setweight(weight);
                aux.setdestination(v2);
                listAdyacencias[v1].add(aux);
                
            }
        } else {
            throw new  ListEmptyException("El vertice no existe");
        }
        
    }


    // sin peso
    public void add_edge(Integer v1, Integer v2) throws Exception {
        this.add_edge(v1, v2, Float.NaN);// Float.NaN significa que no tiene peso

    }

    // Adyancenias
    public LinkedList<Adyacencia> adyacencias(Integer vi) {
        return listAdyacencias[vi];
    }

    public LinkedList<Adyacencia>[] getListAdyacencias() {
        return this.listAdyacencias;
    }


    public void setNro_edges(Integer nro_edges) {
        this.nro_edges = nro_edges;
    }

}
