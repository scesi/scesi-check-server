package scesi.org.check.mqtt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scesi.org.check.mqtt.model.exception.EnrollmentException;
import scesi.org.check.mqtt.model.response.MqttCommandResponse;

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

    public MqttCommandResponse enroll(Integer userId, Integer finger) {
        String key = "enroll:" + userId + ":" + finger;
        CompletableFuture<MqttCommandResponse> future = responseRegistry.register(key, ENROLL_TIMEOUT_MS);
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

    public MqttCommandResponse deleteFinger(Integer userId, Integer finger) {
        String key = "delete:" + userId + ":" + finger;
        CompletableFuture<MqttCommandResponse> future = responseRegistry.register(key, DELETE_TIMEOUT_MS);
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

    public MqttCommandResponse deleteUser(Integer userId) {
        String key = "delete:" + userId + ":0";
        CompletableFuture<MqttCommandResponse> future = responseRegistry.register(key, DELETE_TIMEOUT_MS);
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
}