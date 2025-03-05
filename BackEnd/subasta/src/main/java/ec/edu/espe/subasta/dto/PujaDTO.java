package ec.edu.espe.subasta.dto;

import java.time.LocalDateTime;

public class PujaDTO {
    private Integer idPuja;
    private Integer autoId;
    private Integer compradorId;
    private Double monto;
    private LocalDateTime fecha;
    private String estado;

    public PujaDTO() {}

    public PujaDTO(Integer idPuja, Integer autoId, Integer compradorId, Double monto, LocalDateTime fecha, String estado) {
        this.idPuja = idPuja;
        this.autoId = autoId;
        this.compradorId = compradorId;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdPuja() { return idPuja; }
    public void setIdPuja(Integer idPuja) { this.idPuja = idPuja; }

    public Integer getAutoId() { return autoId; }
    public void setAutoId(Integer autoId) { this.autoId = autoId; }

    public Integer getCompradorId() { return compradorId; }
    public void setCompradorId(Integer compradorId) { this.compradorId = compradorId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
