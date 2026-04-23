/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;


/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

/**
 * Logs every incoming request and every outgoing response.
 *
 * Implements BOTH filter interfaces in one class so we only register
 * one component in SmartCampusApp.
 *
 * WHY FILTERS INSTEAD OF MANUAL LOGGING (report answer):
 *   If we put Logger.info() inside every resource method:
 *     - Every new method needs the same boilerplate — easy to forget
 *     - Format is inconsistent across methods
 *     - To disable all logging you'd have to touch every class
 *     - Exception mappers bypass resource methods entirely, so requests
 *       that throw are never logged
 *
 *   A filter is a cross-cutting concern that runs for EVERY request and
 *   response automatically, including those handled by exception mappers.
 *   Add 50 new endpoints — logging still works with zero extra code.
 *
 * @Provider makes Jersey auto-discover this class.
 */
@Provider
public class LoggingFilter
        implements ContainerRequestFilter, ContainerResponseFilter {
 
    private static final Logger LOG =
            Logger.getLogger(LoggingFilter.class.getName());
 
    /**
     * Runs BEFORE the resource method is called.
     * Logs the HTTP method and full request URI.
     */
    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        LOG.info(String.format(
                "--> %s %s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri()
        ));
    }
 
    /**
     * Runs AFTER the resource method (or exception mapper) returns.
     * Logs the HTTP status code that was returned to the client.
     */
    @Override
    public void filter(ContainerRequestContext  requestContext,
                       ContainerResponseContext responseContext)
            throws IOException {
        LOG.info(String.format(
                "<-- %d %s %s",
                responseContext.getStatus(),
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri()
        ));
    }
}
