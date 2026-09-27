package scesi.org.check.mqtt.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WifiCommandRequest(
        @JsonProperty("action") @NotNull(message = "action is required") String action,
        @JsonProperty("ssid") @NotBlank(message = "ssid is required") @Size(max = 32, message = "ssid cannot exceed 32 characters") String ssid,
        @JsonProperty("pass") @Size(max = 64, message = "pass cannot exceed 64 characters") String pass
) {
    public static WifiCommandRequest add(String ssid, String pass) {
        return new WifiCommandRequest("wifi_add", ssid, pass);
    }

    public static WifiCommandRequest delete(String ssid) {
        return new WifiCommandRequest("wifi_delete", ssid, null);
    }

    public static WifiCommandRequest list() {
        return new WifiCommandRequest("wifi_list", null, null);
    }
}