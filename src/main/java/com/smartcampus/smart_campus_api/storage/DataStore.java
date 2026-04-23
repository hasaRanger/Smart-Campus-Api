/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart_campus_api.storage;

import com.smartcampus.smart_campus_api.model.Room;
import com.smartcampus.smart_campus_api.model.Sensor;
import com.smartcampus.smart_campus_api.model.SensorReading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

public class DataStore {
 
    // Singleton setup 
 
    private static final DataStore INSTANCE = new DataStore();
 
    private DataStore() {}   // prevent external instantiation
 
    public static DataStore getInstance() {
        return INSTANCE;
    }
 
    // Storage maps 
 
    /** roomId - Room */
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
 
    /** sensorId - Sensor */
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
 
    /**
     * sensorId - thread-safe list of SensorReadings.
     * computeIfAbsent ensures we never return null for an unknown sensorId.
     */
    private final Map<String, List<SensorReading>> readings =
            new ConcurrentHashMap<>();
 
    // Accessors 
 
    public Map<String, Room> getRooms() {
        return rooms;
    }
 
    public Map<String, Sensor> getSensors() {
        return sensors;
    }
 
    /**
     * Returns (creating if necessary) the thread-safe readings list
     * for the given sensorId.
     */
    public List<SensorReading> getReadingsForSensor(String sensorId) {
        return readings.computeIfAbsent(
                sensorId,
                k -> Collections.synchronizedList(new ArrayList<>())
        );
    }
}