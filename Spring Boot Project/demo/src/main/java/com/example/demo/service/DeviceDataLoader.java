package com.example.demo.service;

import com.example.demo.Repository.DeviceRepository;
import com.example.demo.model.Device;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Component
public class DeviceDataLoader {

    @Autowired
    private DeviceRepository deviceRepository;

    @PostConstruct
    public void loadData() {
        // Read the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/test_devices.json")) {
            // Parse JSON file into a list of Device objects
            List<Device> devices = objectMapper.readValue(inputStream, new TypeReference<List<Device>>() {});

            // Save each device to the database
            for (Device device : devices) {
                deviceRepository.save(device);
            }
            System.out.println("Data loaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
