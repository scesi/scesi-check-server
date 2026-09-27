package scesi.org.check.mqtt.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WifiCommandResponse(
        String action,
        String ssid,
        Boolean ok,
        String detail,
        List<String> ssids
) {
    public boolean isSuccess() {
        return Boolean.TRUE.equals(ok);
    }

    public static WifiCommandResponse error(String action, String ssid, String detail) {
        return new WifiCommandResponse(action, ssid, false, detail, null);
    }
}