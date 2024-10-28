
package com.example.demo.Controller;

import com.example.demo.model.Device;
import com.example.demo.Repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/")
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return deviceRepository.findById(id)
                .map(device -> ResponseEntity.ok().body(device))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Device createDevice(@RequestBody Device device) {
        return deviceRepository.save(device);
    }

    @PostMapping("/predict/{deviceId}")
    public ResponseEntity<?> predictDevicePrice(@PathVariable Long deviceId) {
        // Fetch the device details
        Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
        if (!optionalDevice.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Device device = optionalDevice.get();
        Map<String, Object> features = prepareFeatureMap(device);

        // Set up the headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity with the features and headers
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(features, headers);

        // Call the Python API for prediction
        RestTemplate restTemplate = new RestTemplate();
        String pythonApiUrl = "http://localhost:5000/api/predict/" + deviceId;

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Extract the predicted price range from the response
                Map<String, Object> responseBody = response.getBody();
                int predictedPriceRange = (int) responseBody.get("predicted_price_range");

                // Save the predicted price to the device
                device.setPredicted_price_range(predictedPriceRange);
                deviceRepository.save(device);

                return ResponseEntity.ok(device);
            } else {
                return ResponseEntity.status(response.getStatusCode()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Prediction failed: " + e.getMessage());
        }
    }


    private Map<String, Object> prepareFeatureMap(Device device) {
        Map<String, Object> features = new HashMap<>();
        features.put("battery_power", device.getBattery_power());
        features.put("blue", device.getBlue());
        features.put("clock_speed", device.getClock_speed());
        features.put("dual_sim", device.getDual_sim());
        features.put("fc", device.getFc());
        features.put("four_g", device.getFour_g());
        features.put("int_memory", device.getInt_memory());
        features.put("m_dep", device.getM_dep());
        features.put("mobile_wt", device.getMobile_wt());
        features.put("n_cores", device.getN_cores());
        features.put("pc", device.getPc());
        features.put("px_height", device.getPx_height());
        features.put("px_width", device.getPx_width());
        features.put("ram", device.getRam());
        features.put("sc_h", device.getSc_h());
        features.put("sc_w", device.getSc_w());
        features.put("talk_time", device.getTalk_time());
        features.put("three_g", device.getThree_g());
        features.put("touch_screen", device.getTouch_screen());
        features.put("wifi", device.getWifi());
        return features;
    }
}
