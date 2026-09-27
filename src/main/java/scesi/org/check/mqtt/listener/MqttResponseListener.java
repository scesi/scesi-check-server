package scesi.org.check.mqtt.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import scesi.org.check.mqtt.model.response.FingerprintCommandResponse;
import scesi.org.check.mqtt.model.response.WifiCommandResponse;
import scesi.org.check.mqtt.service.MqttResponseRegistry;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttResponseListener {

    private final ObjectMapper objectMapper;
    private final MqttResponseRegistry responseRegistry;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleResponse(Message<?> message) {
        String payload = message.getPayload().toString();
        log.debug("Received MQTT response: {}", payload);

        try {
            String action = objectMapper.readTree(payload).get("action").asText();
            if (isWifiAction(action)) {
                WifiCommandResponse response = objectMapper.readValue(payload, WifiCommandResponse.class);
                responseRegistry.completeWifi(response);
            } else {
                FingerprintCommandResponse response = objectMapper.readValue(payload, FingerprintCommandResponse.class);
                responseRegistry.completeFingerprint(response);
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to parse MQTT response: {}", payload, e);
        }
    }

    private boolean isWifiAction(String action) {
        return action.startsWith("wifi_");
    }
}