/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.resources;

import com.smartcampus.smart_campus_api.exception.SensorUnavailableException;
import com.smartcampus.smart_campus_api.model.Sensor;
import com.smartcampus.smart_campus_api.model.SensorReading;
import com.smartcampus.smart_campus_api.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;
    private final DataStore store = DataStore.getInstance();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    //GET /sensors/{sensorId}/readings 

    @GET
    public Response getReadings() {
        Sensor sensor = store.getSensors().get(sensorId);
        if (sensor == null) {
            return errorResponse(404, "Not Found", "Sensor '" + sensorId + "' not found.");
        }
        return Response.ok(store.getReadingsForSensor(sensorId)).build();
    }

    // POST /sensors/{sensorId}/readings 

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = store.getSensors().get(sensorId);
        if (sensor == null) {
            return errorResponse(404, "Not Found", "Sensor '" + sensorId + "' not found.");
        }

        // Block sensors in MAINTENANCE → 403 via SensorUnavailableExceptionMapper
        if ("MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException(sensorId);
        }

        if (reading == null) {
            return errorResponse(400, "Bad Request", "Request body with 'value' is required.");
        }

        // Auto-generate id and timestamp if not provided
        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());

        store.getReadingsForSensor(sensorId).add(reading);

        // Side effect: keep parent sensor's currentValue in sync
        sensor.setCurrentValue(reading.getValue());

        return Response.status(201).entity(reading).build();
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