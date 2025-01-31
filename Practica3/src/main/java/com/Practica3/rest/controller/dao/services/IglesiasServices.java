package com.Practica3.rest.controller.dao.services;
import com.Practica3.rest.controller.dao.IglesiasDao;
import com.Practica3.rest.controller.tda.list.LinkedList;
import com.Practica3.rest.models.Iglesias;

public class IglesiasServices {
    private IglesiasDao obj;

    public IglesiasServices() {
        obj = new IglesiasDao();
    }


    public String calcularCaminoCorto(int origen, int destino, int algoritmo) throws Exception {
        return obj.caminoCorto(origen, destino, algoritmo);
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList<Iglesias> listAll() {
        return obj.getListAll();
    }

    public Iglesias getIglesias() {
        return obj.getIglesias();
    }

    public void setIglesias(Iglesias Iglesias) {
        obj.setIglesias(Iglesias);
    }

    public Iglesias get (Integer id) throws Exception {
        return obj.get(id);
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }
}
