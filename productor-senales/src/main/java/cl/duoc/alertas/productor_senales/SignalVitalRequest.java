package cl.duoc.alertas.productor_senales;

public class SignalVitalRequest {
    private String pacienteRut;
    private String pacienteNombre;
    private String tipoSigno;
    private Double valor;
    private String unidad;
    private Double umbralMinimo;
    private Double umbralMaximo;
    private String observacion;

    public String getPacienteRut() { return pacienteRut; }
    public void setPacienteRut(String pacienteRut) { this.pacienteRut = pacienteRut; }
    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }
    public String getTipoSigno() { return tipoSigno; }
    public void setTipoSigno(String tipoSigno) { this.tipoSigno = tipoSigno; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public Double getUmbralMinimo() { return umbralMinimo; }
    public void setUmbralMinimo(Double umbralMinimo) { this.umbralMinimo = umbralMinimo; }
    public Double getUmbralMaximo() { return umbralMaximo; }
    public void setUmbralMaximo(Double umbralMaximo) { this.umbralMaximo = umbralMaximo; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}