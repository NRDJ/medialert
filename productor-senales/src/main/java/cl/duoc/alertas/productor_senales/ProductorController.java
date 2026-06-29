package cl.duoc.alertas.productor_senales;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
@RequestMapping("/api/productor")
public class ProductorController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.queue.alertas}")
    private String colaAlertas;

    public ProductorController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/senales")
    public ResponseEntity<Map<String, Object>> recibirSenal(@RequestBody SignalVitalRequest request) {
        try {
            boolean esAnomalia = request.getValor() < request.getUmbralMinimo()
                    || request.getValor() > request.getUmbralMaximo();

            if (esAnomalia) {
                String mensaje = objectMapper.writeValueAsString(request);
                rabbitTemplate.convertAndSend(colaAlertas, mensaje);
                return ResponseEntity.ok(Map.of(
                        "status", "ALERTA_ENVIADA",
                        "mensaje", "Anomalia detectada y enviada a la cola",
                        "paciente", request.getPacienteNombre(),
                        "valor", request.getValor()
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "status", "NORMAL",
                        "mensaje", "Senal vital dentro de rango normal",
                        "paciente", request.getPacienteNombre(),
                        "valor", request.getValor()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERROR",
                    "mensaje", e.getMessage()
            ));
        }
    }
}