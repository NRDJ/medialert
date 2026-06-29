package cl.duoc.alertas.productor_senales;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.alertas}")
    private String colaAlertas;

    @Value("${rabbitmq.queue.resumen}")
    private String colaResumen;

    @Bean
    public Queue queueAlertas() {
        return new Queue(colaAlertas, true);
    }

    @Bean
    public Queue queueResumen() {
        return new Queue(colaResumen, true);
    }
}