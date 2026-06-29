package cl.duoc.alertas.consumidor_oracle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorOracleListener {

    private final AlertaVitalRepository repository;
    private final ObjectMapper objectMapper;

    public ConsumidorOracleListener(AlertaVitalRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${rabbitmq.queue.alertas}")
    public void recibirAlerta(String mensaje) {
        try {
            System.out.println("Mensaje recibido de RabbitMQ: " + mensaje);

            java.util.Map<String, Object> datos = objectMapper.readValue(mensaje, java.util.Map.class);

            AlertaVital alerta = new AlertaVital();
            alerta.setPacienteRut((String) datos.get("pacienteRut"));
            alerta.setPacienteNombre((String) datos.get("pacienteNombre"));
            alerta.setTipoSigno((String) datos.get("tipoSigno"));
            alerta.setValor(((Number) datos.get("valor")).doubleValue());
            alerta.setUnidad((String) datos.get("unidad"));
            alerta.setUmbralMinimo(((Number) datos.get("umbralMinimo")).doubleValue());
            alerta.setUmbralMaximo(((Number) datos.get("umbralMaximo")).doubleValue());
            alerta.setObservacion((String) datos.getOrDefault("observacion", ""));

            double valor = alerta.getValor();
            double min = alerta.getUmbralMinimo();
            double max = alerta.getUmbralMaximo();

            if (valor < min) {
                alerta.setGravedad(valor <= min * 0.80 ? "CRITICA" : "ALTA");
                alerta.setMensaje("Valor bajo el umbral minimo para " + alerta.getTipoSigno());
            } else if (valor > max) {
                alerta.setGravedad(valor >= max * 1.20 ? "CRITICA" : "ALTA");
                alerta.setMensaje("Valor sobre el umbral maximo para " + alerta.getTipoSigno());
            } else {
                alerta.setGravedad("MEDIA");
                alerta.setMensaje("Senal vital dentro de rango, se mantiene en observacion");
            }

            alerta.setEstado("NUEVA");
            repository.save(alerta);

            System.out.println("Alerta guardada en Oracle con ID: " + alerta.getId());

        } catch (Exception e) {
            System.err.println("Error procesando mensaje: " + e.getMessage());
        }
    }
}