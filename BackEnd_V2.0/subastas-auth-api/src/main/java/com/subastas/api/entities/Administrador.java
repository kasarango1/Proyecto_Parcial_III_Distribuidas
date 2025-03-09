package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Administrador extends Usuario {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acceso", nullable = false)
    private NivelAcceso nivelAcceso;
    
    private String departamento;
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        setTipoUsuario(TipoUsuario.ADMINISTRADOR);
    }
    
    public enum NivelAcceso {
        TOTAL,
        PARCIAL
    }
} 