package com.subastas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String tipo;
    private Long id;
    private String email;
    private String nombre;
    private String tipoUsuario;
} 