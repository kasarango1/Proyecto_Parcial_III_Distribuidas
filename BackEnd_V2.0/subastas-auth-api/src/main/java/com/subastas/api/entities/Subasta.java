package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subastas")
public class Subasta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;
    
    @Enumerated(EnumType.STRING)
    private EstadoSubasta estado = EstadoSubasta.PENDIENTE;
    
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    private Administrador creadoPor;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    private boolean activo = true;
    
    @OneToMany(mappedBy = "subasta", cascade = CascadeType.ALL)
    private List<SubastaAuto> autosEnSubasta = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
    
    public enum EstadoSubasta {
        PENDIENTE,
        ACTIVA,
        FINALIZADA,
        CANCELADA
    }
} 