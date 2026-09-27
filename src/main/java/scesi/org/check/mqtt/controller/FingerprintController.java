package scesi.org.check.mqtt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.mqtt.model.response.FingerprintCommandResponse;
import scesi.org.check.mqtt.model.response.WifiCommandResponse;
import scesi.org.check.mqtt.service.EnrollmentService;

@RestController
@RequestMapping("/user")
public class FingerprintController {

    private final EnrollmentService enrollmentService;

    public FingerprintController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{userId}/fingerprint")
    public ResponseEntity<StandardResponse<FingerprintCommandResponse>> enrollFingerprint(
            @PathVariable("userId") final Integer userId
    ) {
        FingerprintCommandResponse response = enrollmentService.enroll(userId);
        StandardResponse<FingerprintCommandResponse> standardResponse = StandardResponse.<FingerprintCommandResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Fingerprint enrollment completed")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @DeleteMapping("/{userId}/fingerprint/{finger}")
    public ResponseEntity<StandardResponse<Boolean>> deleteFingerprint(
            @PathVariable("userId") final Integer userId,
            @PathVariable("finger") final Integer finger
    ) {
        FingerprintCommandResponse response = enrollmentService.deleteFinger(userId, finger);
        StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Fingerprint deleted successfully")
                .data(response.isSuccess())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @DeleteMapping("/{userId}/fingerprint")
    public ResponseEntity<StandardResponse<Boolean>> deleteUserFingerprints(
            @PathVariable("userId") final Integer userId
    ) {
        FingerprintCommandResponse response = enrollmentService.deleteUser(userId);
        StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User fingerprints deleted successfully")
                .data(response.isSuccess())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PostMapping("/wifi")
    public ResponseEntity<StandardResponse<WifiCommandResponse>> addWifi(
            @RequestParam("ssid") final String ssid,
            @RequestParam("pass") final String pass
    ) {
        WifiCommandResponse response = enrollmentService.wifiAdd(ssid, pass);
        StandardResponse<WifiCommandResponse> standardResponse = StandardResponse.<WifiCommandResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("WiFi network added successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    @DeleteMapping("/wifi/{ssid}")
    public ResponseEntity<StandardResponse<Boolean>> deleteWifi(
            @PathVariable("ssid") final String ssid
    ) {
        WifiCommandResponse response = enrollmentService.wifiDelete(ssid);
        StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("WiFi network deleted successfully")
                .data(response.isSuccess())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @GetMapping("/wifi")
    public ResponseEntity<StandardResponse<WifiCommandResponse>> listWifi() {
        WifiCommandResponse response = enrollmentService.wifiList();
        StandardResponse<WifiCommandResponse> standardResponse = StandardResponse.<WifiCommandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("WiFi networks retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }
}