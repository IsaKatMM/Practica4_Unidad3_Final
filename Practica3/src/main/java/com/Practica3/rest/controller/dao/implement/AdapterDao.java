package com.Practica3.rest.controller.dao.implement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.lang.reflect.Method;


import com.Practica3.rest.controller.tda.list.LinkedList;
import com.google.gson.Gson;

public class AdapterDao<T> implements InterfazDao<T> {
    private Class clazz;
    private Gson g;
    public static String URL = "media/";

    public AdapterDao(Class clazz) {
        this.clazz = clazz;
        g = new Gson();
    }

    @Override
    public LinkedList listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            list.toList(matrix);

        } catch (Exception e) {

        }
        return list;
    }

    /*
     * public T get(Integer id) throws Exception {
     * LinkedList<T> list = listAll();
     * if (!list.isEmpty()) {
     * T [] matriz = list.toArray();
     * return matriz[id - 1];
     * 
     * }
     * return null;
     * }
     */
    // modificacion del get para la busqueda lineal
    // EN LA PRACTICA HACER EL METODO DE BUSQUEDA BINARIA
    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            T[] matriz = list.toArray();
            for (int i = 0; i < matriz.length; i++) {
                if (getIdent(matriz[i]).intValue() == id.intValue()) {
                    return matriz[i];
                }
            }
        }
        return null;
    }

    // este metodo es de la lista que tenemos para buscar por id
    /// este metodo quiere decir que en el tiempo de ejecucion, quema en getId (para
    // clase normal y herencia o sea superclase), invokame el ojbeto por eso el
    // Integer
    private Integer getIdent(T obj) {
        try {
            Method method = null;
            for (Method m : clazz.getMethods()) {
                if (m.getName().equalsIgnoreCase("getId")) {
                    method = m;
                    break;
                }
            }
            // para las herencias
            if (method == null) {
                for (Method m : clazz.getSuperclass().getMethods()) {
                    if (m.getName().equalsIgnoreCase("getId")) {
                        method = m;
                        break;
                    }
                }
            }
            if (method != null)
                return (Integer) method.invoke(obj);
        } catch (Exception e) {
            // TODO: handle exception
            return -1;
        }
        return -1;
    }

    public void merge(T object, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, index);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public void persist(T object) throws Exception {
        LinkedList<T> list = listAll();
        list.add(object);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    // lee archivo
    private String readFile() throws Exception {
        File file = new File(URL + clazz.getSimpleName() + ".json");

        if (!file.exists()) {
            System.out.println("El archivo no existe, creando uno nuevo: " + file.getAbsolutePath());
            saveFile("[]");
        }

        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString().trim();
    }

    private void saveFile(String data) throws Exception {
        File file = new File(URL + clazz.getSimpleName() + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            System.out.println("Creando el archivo JSON: " + file.getAbsolutePath());
            file.createNewFile();
        }

        try (FileWriter f = new FileWriter(file)) {
            f.write(data);
            f.flush();
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}