package scesi.org.check.mqtt.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MqttCommandRequest {
    private String action;
    private Integer userId;
    private Integer finger;
    private String ssid;
    private String pass;
}