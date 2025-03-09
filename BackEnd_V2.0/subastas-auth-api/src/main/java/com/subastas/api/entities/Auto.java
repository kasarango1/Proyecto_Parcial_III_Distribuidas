package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "autos")
public class Auto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Vendedor vendedor;
    
    @Column(nullable = false)
    private String marca;
    
    @Column(nullable = false)
    private String modelo;
    
    @Column(nullable = false)
    private Integer anio;
    
    private BigDecimal kilometraje;
    
    private String color;
    
    @Column(name = "precio_base", nullable = false)
    private BigDecimal precioBase;
    
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    private EstadoAuto estado = EstadoAuto.DISPONIBLE;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    private boolean activo = true;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
    
    public enum EstadoAuto {
        DISPONIBLE,
        EN_SUBASTA,
        VENDIDO
    }
} 