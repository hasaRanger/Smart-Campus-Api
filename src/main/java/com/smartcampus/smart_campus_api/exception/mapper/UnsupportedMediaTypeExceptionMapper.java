/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.exception.mapper;

import javax.ws.rs.NotSupportedException;
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
 * Maps NotSupportedException to HTTP 415 Unsupported Media Type.
 * Triggered when a client sends a request body in a format other than
 * application/json (e.g., text/plain or application/xml).
 */

@Provider
public class UnsupportedMediaTypeExceptionMapper implements ExceptionMapper<NotSupportedException> {

    @Override
    public Response toResponse(NotSupportedException exception) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", 415);
        error.put("error", "Unsupported Media Type");
        error.put("message", "Content-Type must be 'application/json'. Received format is not supported.");
        error.put("hint", "Set the request header: Content-Type: application/json");

        return Response
                .status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
