package scesi.org.check.mqtt.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MqttCommandResponse {
    private String action;
    private Integer userId;
    private Integer finger;
    private Boolean ok;
    private String detail;
    private String ssid;
    private List<String> ssids;
}