/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.exception.mapper;

import com.smartcampus.smart_campus_api.exception.SensorUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

/**
 * Maps SensorUnavailableException → HTTP 403 Forbidden.
 */
@Provider
public class SensorUnavailableExceptionMapper
        implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 403);
        body.put("error", "Forbidden");
        body.put("message", exception.getMessage());
        body.put("hint", "Update the sensor status to 'ACTIVE' before posting new readings.");

        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
