package com.Practica3.rest;

import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import com.Practica3.rest.controller.dao.services.IglesiasServices;
import com.Practica3.rest.controller.tda.graph.GraphLabelNoDirect;
import com.Practica3.rest.controller.tda.list.LinkedList;
import com.Practica3.rest.models.Iglesias;

import com.Practica3.rest.controller.dao.IglesiasDao;

@Path("/iglesias")
public class IglesiasApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap map = new HashMap<>();
        IglesiasServices ps = new IglesiasServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();

        try {
            IglesiasServices ps = new IglesiasServices();
            ps.getIglesias().setNombre(map.get("nombre").toString());
            ps.getIglesias().setDireccion(map.get("direccion").toString());
            ps.getIglesias().setLatitud(Double.parseDouble(map.get("latitud").toString()));
            ps.getIglesias().setLongitud(Double.parseDouble(map.get("longitud").toString()));
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Iglesia guardada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav en data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            // TODO
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIglesia(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        IglesiasServices ps = new IglesiasServices();
        try {
            ps.setIglesias(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
        map.put("msg", "Ok");
        map.put("data", ps.getIglesias());
        if (ps.getIglesias().getId() == null) {
            map.put("data", "No existe la iglesia con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    // eliminar
    @Path("/delete/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        HashMap res = new HashMap<>();
        try {
            IglesiasServices ps = new IglesiasServices();
            ps.setIglesias(ps.get(id));
            ps.delete(id);
            res.put("msg", "Ok");
            res.put("data", "Eliminado exitoso");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }

    }

    // actualizar
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();
        try {
            IglesiasServices ps = new IglesiasServices();
            ps.getIglesias().setId(Integer.parseInt(map.get("id").toString()));
            ps.getIglesias().setNombre(map.get("nombre").toString());
            ps.getIglesias().setDireccion(map.get("direccion").toString());
            ps.getIglesias().setLatitud(Double.parseDouble(map.get("latitud").toString()));
            ps.getIglesias().setLongitud(Double.parseDouble(map.get("longitud").toString()));

            ps.update();
            res.put("msf", "OK");
            res.put("msg", "Persona registrada correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            System.out.println("Error en sav data " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    ///////////////////// GRAFOS////////////////////////

    @Path("/crearG")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response grafoVerAdmin() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            IglesiasDao iglesiasDao = new IglesiasDao();
            LinkedList<Iglesias> listaIglesias = iglesiasDao.getListAll();
            iglesiasDao.creategraph();
            iglesiasDao.saveGraph();
            res.put("msg", "Grafo generado");
            res.put("lista", listaIglesias.toArray());
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

    @Path("/adyacenciasAl")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response unionesgrafos() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            IglesiasDao iglesiasDao = new IglesiasDao();
            GraphLabelNoDirect<String> graph = iglesiasDao.crearuniosnes();
            System.out.println(graph.toString());
            iglesiasDao.saveGraph();
            res.put("msg", "Grafo actualizado exitosamente");
            res.put("data", graph.toString());
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

    @Path("/mapa")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompleteGraphData() {
        try {
            IglesiasDao iglesiasDao = new IglesiasDao();
            JsonObject graph = iglesiasDao.getGraphData();

            if (graph == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se pudo obtener el grafo, el objeto está vacío")
                        .build();
            }

            System.out.println("Contenido del grafo 43: " + graph.getAsJsonObject());
            return Response.ok(graph.toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            // Manejo de errores
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/grafos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraph() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            IglesiasDao iglesiasDao = new IglesiasDao();
            JsonArray graph = iglesiasDao.obtainWeights();
            res.put("msg", "Grafo obtenido ");
            return Response.ok(graph.toString(), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

    @Path("/caminoCorto/{origen}/{destino}/{algoritmo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularCaminoCorto(@PathParam("origen") int origen,
            @PathParam("destino") int destino,
            @PathParam("algoritmo") int algoritmo) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            IglesiasDao iglesiasDao = new IglesiasDao();
            JsonArray graph = iglesiasDao.obtainWeights();
            String resultado = iglesiasDao.caminoCorto(origen, destino, algoritmo);
            res.put("msg", "Camino corto calculado exitosamente");
            res.put("resultado", resultado);
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

    @Path("/dfs/{origen}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response bfs(@PathParam("origen") int origen) throws Exception {
        HashMap<String, Object> res = new HashMap<>();
        IglesiasDao iglesiasDao = new IglesiasDao();
        JsonArray graph = iglesiasDao.obtainWeights();
        String respuesta = iglesiasDao.dfs(origen);

        try {
            res.put("respuesta", respuesta);
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

}