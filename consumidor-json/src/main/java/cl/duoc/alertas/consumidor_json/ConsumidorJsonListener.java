package cl.duoc.alertas.consumidor_json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class ConsumidorJsonListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${alertas.json.directorio}")
    private String directorio;

    @RabbitListener(queues = "${rabbitmq.queue.resumen}")
    public void recibirResumen(String mensaje) {
        try {
            System.out.println("Mensaje recibido de RabbitMQ: " + mensaje);

            Map<String, Object> datos = objectMapper.readValue(mensaje, Map.class);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = "resumen_" + timestamp + ".json";

            File carpeta = new File(directorio);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File(carpeta, nombreArchivo);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(archivo, datos);

            System.out.println("Archivo JSON generado: " + archivo.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error procesando mensaje: " + e.getMessage());
        }
    }
}