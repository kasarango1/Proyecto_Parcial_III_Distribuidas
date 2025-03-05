package ec.edu.espe.subasta.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subasta")
public class Subasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subasta")
    private Integer idSubasta;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(nullable = false, length = 20)
    private String estado = "PROGRAMADA"; // PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA

    @Column(name = "estado_logico", nullable = false, length = 10)
    private String estadoLogico = "ACTIVO"; // Borrado lógico

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @OneToMany(mappedBy = "subasta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Auto> autos;


    public Subasta() {}

    public Subasta(LocalDateTime fechaInicio, LocalDateTime fechaFin, Vendedor vendedor) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.vendedor = vendedor;
    }

    public Integer getIdSubasta() {
        return idSubasta;
    }

    public void setIdSubasta(Integer idSubasta) {
        this.idSubasta = idSubasta;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoLogico() {
        return estadoLogico;
    }

    public void setEstadoLogico(String estadoLogico) {
        this.estadoLogico = estadoLogico;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public void setAutos(List<Auto> autos) {
        this.autos = autos;
    }
}
