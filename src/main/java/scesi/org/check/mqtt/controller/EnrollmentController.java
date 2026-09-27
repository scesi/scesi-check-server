package scesi.org.check.mqtt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.mqtt.model.response.MqttCommandResponse;
import scesi.org.check.mqtt.service.EnrollmentService;

@RestController
@RequestMapping("/fingerprint")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll/{userId}/{finger}")
    public ResponseEntity<StandardResponse<MqttCommandResponse>> enrollFingerprint(
            @PathVariable("userId") final Integer userId,
            @PathVariable("finger") final Integer finger
    ) {
        MqttCommandResponse response = enrollmentService.enroll(userId, finger);
        StandardResponse<MqttCommandResponse> standardResponse = StandardResponse.<MqttCommandResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Fingerprint enrollment completed")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }
}