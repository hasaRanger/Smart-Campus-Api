/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.exception.mapper;

import com.smartcampus.smart_campus_api.exception.LinkedResourceNotFoundException;
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

@Provider
public class LinkedResourceNotFoundExceptionMapper
        implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 422);
        body.put("error", "Unprocessable Entity");
        body.put("message", exception.getMessage());
        body.put("hint", "Check that the 'roomId' field references a room that exists in the system.");

        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
