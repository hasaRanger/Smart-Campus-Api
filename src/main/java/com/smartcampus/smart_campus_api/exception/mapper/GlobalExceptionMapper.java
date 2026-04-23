/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

/**
 * Catch-all safety net for any unexpected Throwable.
 *
 * SECURITY NOTE (answers Part 5 report question):
 * Exposing raw Java stack traces to API consumers is a significant security risk:
 *  1. Class/package names reveal internal architecture, making it easier for
 *     attackers to craft targeted exploits (e.g., known CVEs in specific versions).
 *  2. File paths and line numbers confirm which code paths are reachable, narrowing
 *     attack surface research.
 *  3. Library version strings in stack frames can be cross-referenced against
 *     known vulnerability databases (NVD, CVEDetails).
 *  4. Exception messages sometimes contain SQL fragments, file system paths, or
 *     credential-adjacent data (e.g., "Connection refused to db-host:5432/appdb").
 * This mapper logs the full trace server-side (for ops teams) while returning
 * only a generic, information-lean 500 response to the client.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        // Log the full stack trace server-side so engineers can diagnose issues
        LOGGER.log(Level.SEVERE,
                "Unhandled exception caught by GlobalExceptionMapper: " + exception.getMessage(),
                exception);

        // Return a generic, non-revealing response to the client
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 500);
        body.put("error", "Internal Server Error");
        body.put("message",
                "An unexpected error occurred. Please contact support or try again later.");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
