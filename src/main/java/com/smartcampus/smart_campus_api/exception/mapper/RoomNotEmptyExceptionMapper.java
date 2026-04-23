/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.exception.mapper;

import com.smartcampus.smart_campus_api.exception.RoomNotEmptyException;
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
 * Maps RoomNotEmptyException → HTTP 409 Conflict.
 *
 * @Provider registers this mapper with the JAX-RS runtime automatically.
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 409);
        body.put("error", "Conflict");
        body.put("message", exception.getMessage());
        body.put("hint", "Reassign or delete all sensors in this room before decommissioning it.");

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
