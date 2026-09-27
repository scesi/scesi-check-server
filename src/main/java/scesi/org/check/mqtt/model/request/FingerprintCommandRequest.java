package scesi.org.check.mqtt.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FingerprintCommandRequest(
        @JsonProperty("action") @NotNull(message = "action is required") String action,
        @JsonProperty("user_id") @NotNull(message = "user_id is required") @Min(value = 1, message = "user_id must be positive") Integer userId,
        @JsonProperty("finger") @Min(value = 1, message = "finger must be positive") Integer finger
) {
    public static FingerprintCommandRequest enroll(Integer userId, Integer finger) {
        return new FingerprintCommandRequest("enroll", userId, finger);
    }

    public static FingerprintCommandRequest deleteFinger(Integer userId, Integer finger) {
        return new FingerprintCommandRequest("delete", userId, finger);
    }

    public static FingerprintCommandRequest deleteUser(Integer userId) {
        return new FingerprintCommandRequest("delete", userId, null);
    }

    public static FingerprintCommandRequest fingerList(Integer userId) {
        return new FingerprintCommandRequest("finger_list", userId, null);
    }
}