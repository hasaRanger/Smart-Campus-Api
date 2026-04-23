/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.exception;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

/**
 * Thrown when a request references a resource (e.g., a roomId) that does not
 * exist in the system. Mapped to HTTP 422 Unprocessable Entity.
 *
 * WHY 422 AND NOT 404 (answers Part 5 report question):
 * HTTP 404 means "the URL you requested was not found." Here, the URL
 * /api/v1/sensors is perfectly valid — the problem is that a *field value*
 * inside the valid request body (roomId) references a non-existent resource.
 * HTTP 422 ("Unprocessable Entity") was designed precisely for this: the request
 * is syntactically correct JSON but semantically invalid because it contains a
 * broken foreign-key reference. Using 422 gives the client accurate signal —
 * "your request was understood but the referenced data doesn't exist" — rather
 * than the ambiguous "the endpoint you're calling doesn't exist" implied by 404.
 */

// 422 — sensor references a non-existent room
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String roomId) {
        super("Room '" + roomId + "' does not exist.");
    }
}
