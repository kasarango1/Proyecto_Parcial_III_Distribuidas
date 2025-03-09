package ec.edu.espe.subasta.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auto")
    private Integer idAuto;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false)
    private Integer año;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(nullable = false, length = 20)
    private String estado = "DISPONIBLE"; // DISPONIBLE, SUBASTADO, VENDIDO

    @Column(name = "estado_logico", nullable = false, length = 10)
    private String estadoLogico = "ACTIVO"; // Borrado lógico

    @ManyToOne
    @JoinColumn(name = "subasta_id")
    @JsonBackReference
    private Subasta subasta;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    @JsonBackReference
    private Vendedor vendedor;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Puja> pujas;

    public Auto() {}

    public Auto(String marca, String modelo, Integer año, Double precioBase, Vendedor vendedor) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precioBase = precioBase;
        this.vendedor = vendedor;
    }

    // Getters y Setters

    public Integer getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(Integer idAuto) {
        this.idAuto = idAuto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
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

    public Subasta getSubasta() {
        return subasta;
    }

    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
}

