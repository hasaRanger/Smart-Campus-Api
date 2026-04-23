package com.smartcampus.smart_campus_api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

import com.smartcampus.smart_campus_api.resources.DiscoveryResource;
import com.smartcampus.smart_campus_api.resources.RoomResource;
import com.smartcampus.smart_campus_api.resources.SensorReadingResource;
import com.smartcampus.smart_campus_api.resources.SensorResource;
import com.smartcampus.smart_campus_api.filter.LoggingFilter;
import com.smartcampus.smart_campus_api.exception.mapper.*;

import org.glassfish.jersey.jackson.JacksonFeature;
/**
 * Name: K. K. Nadeesha Hasaranga
 * ID: 20240675 / w2120076
 */

@ApplicationPath("/api/v1")
public class SmartCampusApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // Resources
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);

        // Exception Mappers
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(UnsupportedMediaTypeExceptionMapper.class);
        classes.add(GlobalExceptionMapper.class);

        // Filters
        classes.add(LoggingFilter.class);

        return classes;
    }
}
