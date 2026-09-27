package scesi.org.check.mqtt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import scesi.org.check.mqtt.model.request.FingerprintCommandRequest;
import scesi.org.check.mqtt.model.request.WifiCommandRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttCommandPublisher {

    private final MqttPahoMessageHandler mqttOutbound;
    private final ObjectMapper objectMapper;

    public void publishEnroll(Integer userId, Integer finger) {
        publish(FingerprintCommandRequest.enroll(userId, finger));
    }

    public void publishDeleteFinger(Integer userId, Integer finger) {
        publish(FingerprintCommandRequest.deleteFinger(userId, finger));
    }

    public void publishDeleteUser(Integer userId) {
        publish(FingerprintCommandRequest.deleteUser(userId));
    }

    public void publishFingerList(Integer userId) {
        publish(FingerprintCommandRequest.fingerList(userId));
    }

    public void publishWifiAdd(String ssid, String pass) {
        publish(WifiCommandRequest.add(ssid, pass));
    }

    public void publishWifiDelete(String ssid) {
        publish(WifiCommandRequest.delete(ssid));
    }

    public void publishWifiList() {
        publish(WifiCommandRequest.list());
    }

    private void publish(Object request) {
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