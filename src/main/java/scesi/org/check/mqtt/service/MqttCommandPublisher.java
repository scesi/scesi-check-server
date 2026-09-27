package scesi.org.check.mqtt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import scesi.org.check.mqtt.model.request.MqttCommandRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttCommandPublisher {

    private final MqttPahoMessageHandler mqttOutbound;
    private final ObjectMapper objectMapper;

    public void publishEnroll(Integer userId, Integer finger) {
        MqttCommandRequest request = MqttCommandRequest.builder()
                .action("enroll")
                .userId(userId)
                .finger(finger)
                .build();
        publish(request);
    }

    public void publishDeleteFinger(Integer userId, Integer finger) {
        MqttCommandRequest request = MqttCommandRequest.builder()
                .action("delete")
                .userId(userId)
                .finger(finger)
                .build();
        publish(request);
    }

    public void publishDeleteUser(Integer userId) {
        MqttCommandRequest request = MqttCommandRequest.builder()
                .action("delete")
                .userId(userId)
                .build();
        publish(request);
    }

    public void publishFingerList(Integer userId) {
        MqttCommandRequest request = MqttCommandRequest.builder()
                .action("finger_list")
                .userId(userId)
                .build();
        publish(request);
    }

    private void publish(MqttCommandRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            mqttOutbound.handleMessage(MessageBuilder.withPayload(json).build());
            log.debug("Published MQTT command: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize MQTT command", e);
            throw new RuntimeException("Failed to publish MQTT command", e);
        }
    }
}