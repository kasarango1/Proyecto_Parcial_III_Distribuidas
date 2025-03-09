package com.subastas.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroVendedorDTO {
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    @NotBlank(message = "El apellido es requerido")
    private String apellido;
    
    private String telefono;
    
    @NotBlank(message = "El RUC es requerido")
    @Size(min = 13, max = 13, message = "El RUC debe tener 13 dígitos")
    private String ruc;
    
    @NotBlank(message = "La dirección del negocio es requerida")
    private String direccionNegocio;
} 