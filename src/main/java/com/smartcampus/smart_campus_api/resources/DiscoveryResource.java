/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Response discover() {
        Map<String, Object> response = new LinkedHashMap<>();

        // ── API metadata ──────────────────────────────────────────────────────
        response.put("apiName", "Smart Campus Sensor & Room Management API");
        response.put("version", "1.0.0");
        response.put("description", "RESTful API for managing campus rooms, sensors, and sensor readings.");

        // ── Admin contact ─────────────────────────────────────────────────────
        Map<String, String> contact = new LinkedHashMap<>();
        contact.put("name", "Smart Campus Admin");
        contact.put("email", "admin@smartcampus.ac.uk");
        contact.put("department", "Facilities Management");
        response.put("contact", contact);

        // ── HATEOAS links (primary resource collections) ──────────────────────
        Map<String, String> links = new LinkedHashMap<>();
        links.put("self",     "/api/v1");
        links.put("rooms",    "/api/v1/rooms");
        links.put("sensors",  "/api/v1/sensors");
        response.put("links", links);

        return Response.ok(response).build();
    }
}
