package com.subastas.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroCompradorDTO {
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
    
    @NotBlank(message = "El DNI es requerido")
    @Size(min = 10, max = 10, message = "El DNI debe tener 10 dígitos")
    private String dni;
    
    @NotBlank(message = "La dirección de envío es requerida")
    private String direccionEnvio;
} 