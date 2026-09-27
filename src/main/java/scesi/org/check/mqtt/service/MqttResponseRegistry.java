package scesi.org.check.mqtt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scesi.org.check.mqtt.model.response.FingerprintCommandResponse;
import scesi.org.check.mqtt.model.response.WifiCommandResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MqttResponseRegistry {

    private final Map<String, CompletableFuture<?>> pendingResponses = new ConcurrentHashMap<>();

    public CompletableFuture<FingerprintCommandResponse> registerFingerprint(String correlationKey, long timeoutMs) {
        CompletableFuture<FingerprintCommandResponse> future = new CompletableFuture<>();
        pendingResponses.put(correlationKey, future);

        future.orTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .whenComplete((_, __) -> pendingResponses.remove(correlationKey));

        return future;
    }

    public CompletableFuture<WifiCommandResponse> registerWifi(String correlationKey, long timeoutMs) {
        CompletableFuture<WifiCommandResponse> future = new CompletableFuture<>();
        pendingResponses.put(correlationKey, future);

        future.orTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .whenComplete((_, __) -> pendingResponses.remove(correlationKey));

        return future;
    }

    public void completeFingerprint(FingerprintCommandResponse response) {
        String key = buildFingerprintKey(response);
        @SuppressWarnings("unchecked")
        CompletableFuture<FingerprintCommandResponse> future = (CompletableFuture<FingerprintCommandResponse>) pendingResponses.remove(key);
        if (future != null) {
            future.complete(response);
            log.debug("Completed fingerprint response for key: {}", key);
        } else {
            log.debug("No pending future for key: {} (may have timed out)", key);
        }
    }

    public void completeWifi(WifiCommandResponse response) {
        String key = buildWifiKey(response);
        @SuppressWarnings("unchecked")
        CompletableFuture<WifiCommandResponse> future = (CompletableFuture<WifiCommandResponse>) pendingResponses.remove(key);
        if (future != null) {
            future.complete(response);
            log.debug("Completed WiFi response for key: {}", key);
        } else {
            log.debug("No pending future for key: {} (may have timed out)", key);
        }
    }

    private String buildFingerprintKey(FingerprintCommandResponse response) {
        int userId = response.userId() != null ? response.userId() : 0;
        if ("finger_list".equals(response.action())) {
            return response.action() + ":" + userId;
        }
        int finger = response.finger() != null ? response.finger() : 0;
        return response.action() + ":" + userId + ":" + finger;
    }

    private String buildWifiKey(WifiCommandResponse response) {
        String ssid = response.ssid() != null ? response.ssid() : "0";
        return response.action() + ":" + ssid;
    }
}