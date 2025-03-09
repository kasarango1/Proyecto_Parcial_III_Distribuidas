package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pujas")
public class Puja {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_subasta_auto", nullable = false)
    private SubastaAuto subastaAuto;
    
    @ManyToOne
    @JoinColumn(name = "id_comprador", nullable = false)
    private Comprador comprador;
    
    @Column(nullable = false)
    private BigDecimal monto;
    
    @Column(name = "fecha_puja")
    private LocalDateTime fechaPuja;
    
    @Enumerated(EnumType.STRING)
    private EstadoPuja estado = EstadoPuja.ACTIVA;
    
    @PrePersist
    protected void onCreate() {
        fechaPuja = LocalDateTime.now();
    }
    
    public enum EstadoPuja {
        ACTIVA,
        GANADORA,
        PERDEDORA
    }
} 