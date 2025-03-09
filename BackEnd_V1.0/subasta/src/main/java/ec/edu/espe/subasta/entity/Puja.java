package ec.edu.espe.subasta.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "puja")
public class Puja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puja")
    private Integer idPuja;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    @JsonBackReference
    private Auto auto;

    @ManyToOne
    @JoinColumn(name = "comprador_id", nullable = false)
    @JsonBackReference
    private Comprador comprador;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 20)
    private String estado = "ACTIVA"; // Puede ser: ACTIVA, INVALIDADA

    public Puja() {}

    public Puja(Auto auto, Comprador comprador, Double monto) {
        this.auto = auto;
        this.comprador = comprador;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters

    public Integer getIdPuja() {
        return idPuja;
    }

    public void setIdPuja(Integer idPuja) {
        this.idPuja = idPuja;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

