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
 * Thrown when a POST reading is attempted on a sensor in MAINTENANCE status.
 * Mapped to HTTP 403 Forbidden by SensorUnavailableExceptionMapper.
 */
// 403 - sensor is in MAINTENANCE state
public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String sensorId) {
        super("Sensor " + sensorId + " is under maintenance.");
    }
}
