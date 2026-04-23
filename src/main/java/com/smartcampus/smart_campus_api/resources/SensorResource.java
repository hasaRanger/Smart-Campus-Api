/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.resources;

import com.smartcampus.smart_campus_api.exception.LinkedResourceNotFoundException;
import com.smartcampus.smart_campus_api.model.Sensor;
import com.smartcampus.smart_campus_api.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private final DataStore store = DataStore.getInstance();

    // POST /sensors 

    @POST
    public Response createSensor(Sensor sensor) {
        if (sensor == null || sensor.getId() == null || sensor.getRoomId() == null) {
            return errorResponse(400, "Bad Request", "Fields 'id' and 'roomId' are required.");
        }
        // Validate that the referenced roomId actually exists → 422 if not
        if (!store.getRooms().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException(sensor.getRoomId());
        }
        if (store.getSensors().containsKey(sensor.getId())) {
            return errorResponse(409, "Conflict", "Sensor ID '" + sensor.getId() + "' already exists.");
        }

        store.getSensors().put(sensor.getId(), sensor);

        // Keep Room's sensorIds list in sync
        store.getRooms().get(sensor.getRoomId()).getSensorIds().add(sensor.getId());

        return Response.status(201).entity(sensor).build();
    }

    // GET /sensors (with optional ?type= filter) 

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> result = store.getSensors().values().stream()
            .filter(s -> type == null || s.getType().equalsIgnoreCase(type))
            .collect(Collectors.toList());
        return Response.ok(result).build();
    }

    // GET /sensors/{id} 

    @GET
    @Path("/{id}")
    public Response getSensorById(@PathParam("id") String id) {
        Sensor sensor = store.getSensors().get(id);
        if (sensor == null) {
            return errorResponse(404, "Not Found", "Sensor '" + id + "' not found.");
        }
        return Response.ok(sensor).build();
    }

    // Sub-resource locator: /sensors/{sensorId}/readings 

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(
            @PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
    
    @PUT
    @Path("/{id}")
    public Response updateSensor(@PathParam("id") String id, Sensor updated) {
        Sensor existing = store.getSensors().get(id);
        if (existing == null) {
            return errorResponse(404, "Not Found", "Sensor '" + id + "' not found.");
        }
        if (updated.getType() != null) {
            existing.setType(updated.getType());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        return Response.ok(existing).build();
    }

    // Helper 
    private Response errorResponse(int status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        return Response.status(status).entity(body).build();
    }
}