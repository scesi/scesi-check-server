package scesi.org.check.mqtt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scesi.org.check.mqtt.model.exception.EnrollmentException;
import scesi.org.check.mqtt.model.response.FingerprintCommandResponse;
import scesi.org.check.mqtt.model.response.WifiCommandResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final MqttCommandPublisher publisher;
    private final MqttResponseRegistry responseRegistry;

    private static final long ENROLL_TIMEOUT_MS = 15_000;
    private static final long DELETE_TIMEOUT_MS = 5_000;
    private static final long FINGER_LIST_TIMEOUT_MS = 5_000;
    private static final long WIFI_TIMEOUT_MS = 5_000;
    private static final int MAX_FINGERS_PER_USER = 2;

    public FingerprintCommandResponse enroll(Integer userId) {
        FingerprintCommandResponse fingerList = fingerList(userId);
        if (!fingerList.isSuccess()) {
            throw new EnrollmentException("Failed to get finger list: " + fingerList.detail());
        }

        List<Integer> usedFingers = fingerList.fingers();
        Integer nextFinger = findNextAvailableFinger(usedFingers);

        if (nextFinger == null) {
            throw new EnrollmentException("User already has max fingers (" + MAX_FINGERS_PER_USER + ")");
        }

        return enroll(userId, nextFinger);
    }

    public FingerprintCommandResponse enroll(Integer userId, Integer finger) {
        String key = "enroll:" + userId + ":" + finger;
        CompletableFuture<FingerprintCommandResponse> future = responseRegistry.registerFingerprint(key, ENROLL_TIMEOUT_MS);
        publisher.publishEnroll(userId, finger);

        try {
            return future.get(ENROLL_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Enrollment interrupted for user {} finger {}", userId, finger, e);
            throw new EnrollmentException("Enrollment interrupted", e);
        } catch (ExecutionException e) {
            log.error("Enrollment execution failed for user {} finger {}", userId, finger, e);
            throw new EnrollmentException("Enrollment failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("Enrollment timeout for user {} finger {}", userId, finger);
            throw new EnrollmentException("Enrollment timeout after " + ENROLL_TIMEOUT_MS + "ms", e);
        }
    }

    public FingerprintCommandResponse fingerList(Integer userId) {
        String key = "finger_list:" + userId;
        CompletableFuture<FingerprintCommandResponse> future = responseRegistry.registerFingerprint(key, FINGER_LIST_TIMEOUT_MS);
        publisher.publishFingerList(userId);

        try {
            return future.get(FINGER_LIST_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Finger list interrupted for user {}", userId, e);
            throw new EnrollmentException("Finger list interrupted", e);
        } catch (ExecutionException e) {
            log.error("Finger list execution failed for user {}", userId, e);
            throw new EnrollmentException("Finger list failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("Finger list timeout for user {}", userId);
            throw new EnrollmentException("Finger list timeout after " + FINGER_LIST_TIMEOUT_MS + "ms", e);
        }
    }

    public FingerprintCommandResponse deleteFinger(Integer userId, Integer finger) {
        String key = "delete:" + userId + ":" + finger;
        CompletableFuture<FingerprintCommandResponse> future = responseRegistry.registerFingerprint(key, DELETE_TIMEOUT_MS);
        publisher.publishDeleteFinger(userId, finger);

        try {
            return future.get(DELETE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Delete finger interrupted for user {} finger {}", userId, finger, e);
            throw new EnrollmentException("Delete finger interrupted", e);
        } catch (ExecutionException e) {
            log.error("Delete finger execution failed for user {} finger {}", userId, finger, e);
            throw new EnrollmentException("Delete finger failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("Delete finger timeout for user {} finger {}", userId, finger);
            throw new EnrollmentException("Delete finger timeout after " + DELETE_TIMEOUT_MS + "ms", e);
        }
    }

    public FingerprintCommandResponse deleteUser(Integer userId) {
        String key = "delete:" + userId + ":0";
        CompletableFuture<FingerprintCommandResponse> future = responseRegistry.registerFingerprint(key, DELETE_TIMEOUT_MS);
        publisher.publishDeleteUser(userId);

        try {
            return future.get(DELETE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Delete user interrupted for user {}", userId, e);
            throw new EnrollmentException("Delete user interrupted", e);
        } catch (ExecutionException e) {
            log.error("Delete user execution failed for user {}", userId, e);
            throw new EnrollmentException("Delete user failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("Delete user timeout for user {}", userId);
            throw new EnrollmentException("Delete user timeout after " + DELETE_TIMEOUT_MS + "ms", e);
        }
    }

    public WifiCommandResponse wifiAdd(String ssid, String pass) {
        String key = "wifi_add:" + ssid;
        CompletableFuture<WifiCommandResponse> future = responseRegistry.registerWifi(key, WIFI_TIMEOUT_MS);
        publisher.publishWifiAdd(ssid, pass);

        try {
            return future.get(WIFI_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("WiFi add interrupted for ssid {}", ssid, e);
            throw new EnrollmentException("WiFi add interrupted", e);
        } catch (ExecutionException e) {
            log.error("WiFi add execution failed for ssid {}", ssid, e);
            throw new EnrollmentException("WiFi add failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("WiFi add timeout for ssid {}", ssid);
            throw new EnrollmentException("WiFi add timeout after " + WIFI_TIMEOUT_MS + "ms", e);
        }
    }

    public WifiCommandResponse wifiDelete(String ssid) {
        String key = "wifi_delete:" + ssid;
        CompletableFuture<WifiCommandResponse> future = responseRegistry.registerWifi(key, WIFI_TIMEOUT_MS);
        publisher.publishWifiDelete(ssid);

        try {
            return future.get(WIFI_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("WiFi delete interrupted for ssid {}", ssid, e);
            throw new EnrollmentException("WiFi delete interrupted", e);
        } catch (ExecutionException e) {
            log.error("WiFi delete execution failed for ssid {}", ssid, e);
            throw new EnrollmentException("WiFi delete failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("WiFi delete timeout for ssid {}", ssid);
            throw new EnrollmentException("WiFi delete timeout after " + WIFI_TIMEOUT_MS + "ms", e);
        }
    }

    public WifiCommandResponse wifiList() {
        String key = "wifi_list:0";
        CompletableFuture<WifiCommandResponse> future = responseRegistry.registerWifi(key, WIFI_TIMEOUT_MS);
        publisher.publishWifiList();

        try {
            return future.get(WIFI_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("WiFi list interrupted", e);
            throw new EnrollmentException("WiFi list interrupted", e);
        } catch (ExecutionException e) {
            log.error("WiFi list execution failed", e);
            throw new EnrollmentException("WiFi list failed: " + e.getCause().getMessage(), e);
        } catch (TimeoutException e) {
            log.error("WiFi list timeout");
            throw new EnrollmentException("WiFi list timeout after " + WIFI_TIMEOUT_MS + "ms", e);
        }
    }

    private Integer findNextAvailableFinger(List<Integer> usedFingers) {
        for (int i = 1; i <= MAX_FINGERS_PER_USER; i++) {
            if (!usedFingers.contains(i)) {
                return i;
            }
        }
        return null;
    }
}