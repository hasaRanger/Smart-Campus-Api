/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.resources;

import com.smartcampus.smart_campus_api.exception.RoomNotEmptyException;
import com.smartcampus.smart_campus_api.model.Room;
import com.smartcampus.smart_campus_api.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 *
 * Handles all /api/v1/rooms endpoints.
 *
 *  GET    /api/v1/rooms          → list all rooms
 *  POST   /api/v1/rooms          → create a new room
 *  GET    /api/v1/rooms/{id}     → get a single room
 *  DELETE /api/v1/rooms/{id}     → delete a room (blocked if sensors exist)
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    private final DataStore store = DataStore.getInstance();

    // ── GET /rooms ────────────────────────────────────────────────────────────

    @GET
    public Response getAllRooms() {
        return Response.ok(store.getRooms().values()).build();
    }

    // ── POST /rooms ───────────────────────────────────────────────────────────

    @POST
    public Response createRoom(Room room) {
        if (room == null || room.getId() == null || room.getName() == null) {
            return errorResponse(400, "Bad Request", "Fields 'id' and 'name' are required.");
        }
        if (store.getRooms().containsKey(room.getId())) {
            return errorResponse(409, "Conflict", "Room ID '" + room.getId() + "' already exists.");
        }
        store.getRooms().put(room.getId(), room);
        return Response.status(201).entity(room).build();
    }

    // ── GET /rooms/{id} ───────────────────────────────────────────────────────

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        Room room = store.getRooms().get(id);
        if (room == null) {
            return errorResponse(404, "Not Found", "Room '" + id + "' not found.");
        }
        return Response.ok(room).build();
    }

    // ── DELETE /rooms/{id} ────────────────────────────────────────────────────

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = store.getRooms().get(id);
        if (room == null) {
            return errorResponse(404, "Not Found", "Room '" + id + "' not found.");
        }
        // Business rule: cannot delete room that still has sensors
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException(id);
        }
        store.getRooms().remove(id);
        return Response.noContent().build(); // 204
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Response errorResponse(int status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        return Response.status(status).entity(body).build();
    }
}