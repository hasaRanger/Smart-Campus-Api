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

@Provider
public class LoggingFilter
        implements ContainerRequestFilter, ContainerResponseFilter {
 
    private static final Logger LOG =
            Logger.getLogger(LoggingFilter.class.getName());
 
    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        LOG.info(String.format(
                "--> %s %s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getRequestUri()
        ));
    }
 
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
