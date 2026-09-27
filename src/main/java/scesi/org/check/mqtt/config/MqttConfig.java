package scesi.org.check.mqtt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttConfig {

    @Value("${spring.mqtt.broker-url}")
    private String brokerUrl;

    @Value("${spring.mqtt.client-id}")
    private String clientId;

    @Value("${spring.mqtt.username:}")
    private String username;

    @Value("${spring.mqtt.password:}")
    private String password;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        if (!username.isEmpty()) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MqttPahoMessageHandler mqttOutboundHandler(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(clientId + "-pub", factory);
        handler.setAsync(true);
        handler.setDefaultTopic("lector/cmd");
        return handler;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound(MqttPahoClientFactory factory, MessageChannel mqttInputChannel) {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                clientId + "-sub", factory, "lector/resp");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}