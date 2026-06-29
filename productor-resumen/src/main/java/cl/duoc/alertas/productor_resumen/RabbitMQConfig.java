package cl.duoc.alertas.productor_resumen;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.resumen}")
    private String colaResumen;

    @Bean
    public Queue queueResumen() {
        return new Queue(colaResumen, true);
    }
}