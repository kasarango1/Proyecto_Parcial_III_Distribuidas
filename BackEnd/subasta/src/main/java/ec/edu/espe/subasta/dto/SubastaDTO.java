package ec.edu.espe.subasta.dto;

import java.time.LocalDateTime;

public class SubastaDTO {
    private Integer idSubasta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private Integer vendedorId;

    public SubastaDTO() {}

    public SubastaDTO(Integer idSubasta, LocalDateTime fechaInicio, LocalDateTime fechaFin, String estado, Integer vendedorId) {
        this.idSubasta = idSubasta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.vendedorId = vendedorId;
    }

    // Getters y Setters
    public Integer getIdSubasta() { return idSubasta; }
    public void setIdSubasta(Integer idSubasta) { this.idSubasta = idSubasta; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getVendedorId() { return vendedorId; }
    public void setVendedorId(Integer vendedorId) { this.vendedorId = vendedorId; }
}
