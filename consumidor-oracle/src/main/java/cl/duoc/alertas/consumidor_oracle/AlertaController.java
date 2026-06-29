package cl.duoc.alertas.consumidor_oracle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaVitalRepository repository;

    public AlertaController(AlertaVitalRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<AlertaVital>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaVital> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlertaVital> crear(@RequestBody AlertaVital alerta) {
        AlertaVital guardada = repository.save(alerta);
        return ResponseEntity.created(URI.create("/api/alertas/" + guardada.getId())).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaVital> actualizar(@PathVariable Long id, @RequestBody AlertaVital datos) {
        return repository.findById(id).map(alerta -> {
            alerta.setEstado(datos.getEstado());
            alerta.setObservacion(datos.getObservacion());
            alerta.setGravedad(datos.getGravedad());
            return ResponseEntity.ok(repository.save(alerta));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}