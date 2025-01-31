package com.Practica3.rest.controller.tda.list;
//T, E, K, V son datos genericos, generico significa cualquier dato
//el dato generico E es para colecciones ya que la lista enlazada es una coleccion
public class Node <E> {
    private E info;
    private Node <E> next;

    //apunta al nulo
    public Node (E info){
        this.info= info;
        this.next= null;
    }
    //apunta al siguiente
    public Node (E info, Node <E> next){
        this.info= info;
        this.next= null;
    }

    //GETTERS AND SETTERS FOR JAVA 
    public E getInfo() {
        return this.info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Node<E> getNext() {
        return this.next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
    
}
