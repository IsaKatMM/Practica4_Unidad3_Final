package com.Practica3.rest.controller.tda.list;
import java.lang.reflect.Method;

import com.Practica3.rest.controller.tda.list.Exception.ListEmptyException;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last; 
    private Integer size; 
    public static Integer ASC =1;
    public static Integer DESC =0;

    public LinkedList() {
        this.header = null; 
        this.last = null; 
        this.size = 0; 
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato); 
            header = help; 
            this.size++; 
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help; 
        if (isEmpty()) { 
            help = new Node<>(info); 
            header = help; 
            last = help; 
        } else {
            help = new Node<>(info, null); 
            last.setNext(help); 
            last = help; 
        }
        this.size++; 
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return last.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BYPOSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header; 
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header; 
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }

        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E data, Integer post) throws Exception{
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }else if (post < 0 || post >= size){
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        }else if (post == 0){
            header.setInfo(data);
        }else if (post == (size-1)){
            last.setInfo(data);
        }else{
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }

    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;   
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
            }
    }


    public E delete (Integer post) throws Exception{
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size -1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }

// este metodo implemento el metodo de insercion
//este metodo nossirev paa datos de tipo number, y de tipo string
//solo en la lista se ordena con datos primitivo, si es por objetos es en los daos
    public LinkedList<E> order(String attribute, Integer type) throws Exception{
        if (!isEmpty()){
            E data =this.header.getInfo();
            if(data instanceof Object){
                E[] lista= this.toArray();
                reset();
                for (int i=1; i< lista.length; i++){
                    E aux= lista[i];//valor a ordenar
                    int j=1 -1 ; //indice anterior
                    while (j >= 0 && atrribute_compare(attribute, lista[j], aux, type)){
                        lista[j+1]= lista[j--];//desplaza elementos hacia la derecha 
                    }
                    lista[j+1]=aux; //inserta el valor en su posicion correcta
                }
                this.toList(lista);

            }
        }
        return this;
    }


    private Boolean compare (Object a, Object b, Integer type){
        switch (type) {
            case 0:
            if (a instanceof Number){
                Number a1 = (Number) a;
                Number b1 = (Number) b;
                return a1.doubleValue() > b1.doubleValue();
            } else{
                return (a.toString()).compareTo(b.toString()) > 0;
            }
            default:
            //mayor a menor
            if (a instanceof Number){
                Number a1 = (Number) a;
                Number b1 = (Number) b;
                return a1.doubleValue() < b1.doubleValue();
            } else{
                return (a.toString()).compareTo(b.toString()) < 0;
            }
                
        }
       
    }

    // compare class
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception{
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }


    private Object exist_attribute(E a, String attribute) throws Exception{
         Method method = null;
         attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
         attribute = "get" + attribute;
         for (Method aux : a.getClass().getMethods()){
            if (aux.getName().contains(attribute)){
                method = aux;
                break;
            }
         }
         if (method == null){
            for(Method aux : a.getClass().getSuperclass().getMethods()){
                if (aux.getName().contains(attribute)){
                    method = aux;
                    break;
                }
            }
         }
         if (method == null){
            return method.invoke(a);
         }
            return null;
    }


    

}
 