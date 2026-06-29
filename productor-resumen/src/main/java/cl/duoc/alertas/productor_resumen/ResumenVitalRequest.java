package cl.duoc.alertas.productor_resumen;

import java.util.List;

public class ResumenVitalRequest {
    private String pacienteRut;
    private String pacienteNombre;
    private String periodo;
    private List<String> signosVitales;
    private String observacion;

    public String getPacienteRut() { return pacienteRut; }
    public void setPacienteRut(String pacienteRut) { this.pacienteRut = pacienteRut; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public List<String> getSignosVitales() { return signosVitales; }
    public void setSignosVitales(List<String> signosVitales) { this.signosVitales = signosVitales; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}