package com.subastas.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "vendedores")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Vendedor extends Usuario {
    
    @Column(length = 13)
    private String ruc;
    
    @Column(name = "direccion_negocio")
    private String direccionNegocio;
    
    @PrePersist
    protected void onCreate() {
        super.onCreate();
        setTipoUsuario(TipoUsuario.VENDEDOR);
    }
} 