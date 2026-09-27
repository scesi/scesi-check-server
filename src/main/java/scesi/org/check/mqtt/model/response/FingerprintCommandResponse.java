package scesi.org.check.mqtt.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FingerprintCommandResponse(
        @JsonProperty("action") String action,
        @JsonProperty("user_id") Integer userId,
        @JsonProperty("finger") Integer finger,
        @JsonProperty("ok") Boolean ok,
        @JsonProperty("detail") String detail,
        @JsonProperty("count") Integer count,
        @JsonProperty("max") Integer max,
        @JsonProperty("fingers") List<Integer> fingers
) {
    public boolean isSuccess() {
        return Boolean.TRUE.equals(ok);
    }

    public static FingerprintCommandResponse error(String action, Integer userId, String detail) {
        return new FingerprintCommandResponse(action, userId, null, false, detail, null, null, null);
    }
}