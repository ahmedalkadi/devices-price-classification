////
////
////package com.example.demo.Controller;
////
////import com.example.demo.model.Device;
////import com.example.demo.Repository.DeviceRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/devices")
////public class DeviceController {
////
////    @Autowired
////    private DeviceRepository deviceRepository;
////
////    @GetMapping("/")
////    public List<Device> getAllDevices() {
////        return deviceRepository.findAll();
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
////        return deviceRepository.findById(id)
////                .map(device -> ResponseEntity.ok().body(device))
////                .orElse(ResponseEntity.notFound().build());
////    }
////
////    @PostMapping("/")
////    public Device createDevice(@RequestBody Device device) {
////        return deviceRepository.save(device);
////    }
////
////    @PostMapping("/predict/{deviceId}")
////    public ResponseEntity<?> predictDevicePrice(@PathVariable Long deviceId) {
////        // Call your Python API for prediction
////        // Update the device entity with the predicted price range
////        // Return the updated device entity
////        return ResponseEntity.ok().build();
////    }
////}
//
//package com.example.demo.Controller;
//
//import com.example.demo.model.Device;
//import com.example.demo.Repository.DeviceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/devices")
//public class DeviceController {
//
//    @Autowired
//    private DeviceRepository deviceRepository;
//
//    @GetMapping("/")
//    public List<Device> getAllDevices() {
//        return deviceRepository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
//        return deviceRepository.findById(id)
//                .map(device -> ResponseEntity.ok().body(device))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping("/")
//    public Device createDevice(@RequestBody Device device) {
//        return deviceRepository.save(device);
//    }
//
//    @PostMapping("/predict/{deviceId}")
//    public ResponseEntity<?> predictDevicePrice(@PathVariable Long deviceId) {
//        // Fetch the device details
//        Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
//        if (!optionalDevice.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        Device device = optionalDevice.get();
//
//        // Prepare the data to be sent to the Python API
//        Map<String, Object> features = new HashMap<>();
//        features.put("battery_power", device.getBattery_power());
//        features.put("blue", device.getBlue());
//        features.put("clock_speed", device.getClock_speed());
//        features.put("dual_sim", device.getDual_sim());
//        features.put("fc", device.getFc());
//        features.put("four_g", device.getFour_g());
//        features.put("int_memory", device.getInt_memory());
//        features.put("m_dep", device.getM_dep());
//        features.put("mobile_wt", device.getMobile_wt());
//        features.put("n_cores", device.getN_cores());
//        features.put("pc", device.getPc());
//        features.put("px_height", device.getPx_height());
//        features.put("px_width", device.getPx_width());
//        features.put("ram", device.getRam());
//        features.put("sc_h", device.getSc_h());
//        features.put("sc_w", device.getSc_w());
//        features.put("talk_time", device.getTalk_time());
//        features.put("three_g", device.getThree_g());
//        features.put("touch_screen", device.getTouch_screen());
//        features.put("wifi", device.getWifi());
//
//        // Call the Python API for prediction
//        RestTemplate restTemplate = new RestTemplate();
//        String pythonApiUrl = "http://localhost:5000/predict";
//        ResponseEntity<Integer> response = restTemplate.postForEntity(pythonApiUrl, features, Integer.class);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            int predictedPriceRange = response.getBody();
//
//            // Update the device with the predicted price range
//            device.setPredicted_price_range(predictedPriceRange);
//            deviceRepository.save(device);
//
//            // Return the updated device entity
//            return ResponseEntity.ok(device);
//        } else {
//            return ResponseEntity.status(response.getStatusCode()).build();
//        }
//    }
//}
package com.example.demo.Controller;

import com.example.demo.model.Device;
import com.example.demo.Repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Call the Python API for prediction
        RestTemplate restTemplate = new RestTemplate();
        String pythonApiUrl = "http://localhost:5000/predict";
        ResponseEntity<Integer> response = restTemplate.postForEntity(pythonApiUrl, features, Integer.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            int predictedPriceRange = response.getBody();
            device.setPredicted_price_range(predictedPriceRange);
            deviceRepository.save(device);
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
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
