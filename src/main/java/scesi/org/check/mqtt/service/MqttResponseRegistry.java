package scesi.org.check.mqtt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scesi.org.check.mqtt.model.response.MqttCommandResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MqttResponseRegistry {

    private final Map<String, CompletableFuture<MqttCommandResponse>> pendingResponses = new ConcurrentHashMap<>();

    public CompletableFuture<MqttCommandResponse> register(String correlationKey, long timeoutMs) {
        CompletableFuture<MqttCommandResponse> future = new CompletableFuture<>();
        pendingResponses.put(correlationKey, future);

        future.orTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .whenComplete((_, __) -> pendingResponses.remove(correlationKey));

        return future;
    }

    public void complete(MqttCommandResponse response) {
        String key = buildKey(response);
        CompletableFuture<MqttCommandResponse> future = pendingResponses.remove(key);
        if (future != null) {
            future.complete(response);
            log.debug("Completed response for key: {}", key);
        } else {
            log.debug("No pending future for key: {} (may have timed out)", key);
        }
    }

    private String buildKey(MqttCommandResponse response) {
        int userId = response.getUserId() != null ? response.getUserId() : 0;
        if ("finger_list".equals(response.getAction())) {
            return response.getAction() + ":" + userId;
        }
        int finger = response.getFinger() != null ? response.getFinger() : 0;
        return response.getAction() + ":" + userId + ":" + finger;
    }
}