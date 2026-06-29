package cl.duoc.alertas.productor_resumen;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/productor")
public class ProductorResumenController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.queue.resumen}")
    private String colaResumen;

    public ProductorResumenController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/resumen")
    public ResponseEntity<Map<String, Object>> enviarResumen(@RequestBody ResumenVitalRequest request) {
        try {
            Map<String, Object> resumen = Map.of(
                    "pacienteRut", request.getPacienteRut(),
                    "pacienteNombre", request.getPacienteNombre(),
                    "periodo", request.getPeriodo(),
                    "signosVitales", request.getSignosVitales(),
                    "observacion", request.getObservacion() != null ? request.getObservacion() : "",
                    "fechaResumen", LocalDateTime.now().toString()
            );

            String mensaje = objectMapper.writeValueAsString(resumen);
            rabbitTemplate.convertAndSend(colaResumen, mensaje);

            return ResponseEntity.ok(Map.of(
                    "status", "RESUMEN_ENVIADO",
                    "mensaje", "Resumen periodico enviado a la cola",
                    "paciente", request.getPacienteNombre(),
                    "periodo", request.getPeriodo()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERROR",
                    "mensaje", e.getMessage()
            ));
        }
    }
}