package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subasta_autos")
public class SubastaAuto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_subasta", nullable = false)
    private Subasta subasta;
    
    @ManyToOne
    @JoinColumn(name = "id_auto", nullable = false)
    private Auto auto;
    
    @Column(name = "precio_final")
    private BigDecimal precioFinal;
    
    @Enumerated(EnumType.STRING)
    private EstadoSubastaAuto estado = EstadoSubastaAuto.PENDIENTE;
    
    @OneToMany(mappedBy = "subastaAuto", cascade = CascadeType.ALL)
    private List<Puja> pujas = new ArrayList<>();
    
    public enum EstadoSubastaAuto {
        PENDIENTE,
        VENDIDO,
        NO_VENDIDO
    }
} 