package scesi.org.check.mqtt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.mqtt.model.response.MqttCommandResponse;
import scesi.org.check.mqtt.service.EnrollmentService;

@RestController
@RequestMapping("/user")
public class FingerprintController {

    private final EnrollmentService enrollmentService;

    public FingerprintController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{userId}/fingerprint")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> enrollFingerprint(
            @PathVariable("userId") final Integer userId
    ) {
        MqttCommandResponse response = enrollmentService.enroll(userId);
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Fingerprint enrollment completed")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @DeleteMapping("/{userId}/fingerprint/{finger}")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> deleteFingerprint(
            @PathVariable("userId") final Integer userId,
            @PathVariable("finger") final Integer finger
    ) {
        MqttCommandResponse response = enrollmentService.deleteFinger(userId, finger);
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Fingerprint deleted successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @DeleteMapping("/{userId}/fingerprint")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> deleteUserFingerprints(
            @PathVariable("userId") final Integer userId
    ) {
        MqttCommandResponse response = enrollmentService.deleteUser(userId);
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User fingerprints deleted successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PostMapping("/wifi")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> addWifi(
            @RequestParam("ssid") final String ssid,
            @RequestParam("pass") final String pass
    ) {
        MqttCommandResponse response = enrollmentService.wifiAdd(ssid, pass);
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("WiFi network added successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @DeleteMapping("/wifi/{ssid}")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> deleteWifi(
            @PathVariable("ssid") final String ssid
    ) {
        MqttCommandResponse response = enrollmentService.wifiDelete(ssid);
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("WiFi network deleted successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @GetMapping("/wifi")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> listWifi() {
        MqttCommandResponse response = enrollmentService.wifiList();
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("WiFi networks retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }
}