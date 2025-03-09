package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "compradores")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Comprador extends Usuario {
    
    @Column(length = 10)
    private String dni;
    
    @Column(name = "direccion_envio")
    private String direccionEnvio;
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        setTipoUsuario(TipoUsuario.COMPRADOR);
    }
} 