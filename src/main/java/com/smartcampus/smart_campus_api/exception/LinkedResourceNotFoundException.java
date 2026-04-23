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
 */

// 422 - sensor references a non-existent room
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String roomId) {
        super("Room '" + roomId + "' does not exist.");
    }
}
