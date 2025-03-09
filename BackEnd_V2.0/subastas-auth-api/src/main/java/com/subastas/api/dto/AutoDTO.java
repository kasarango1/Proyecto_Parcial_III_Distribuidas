package com.subastas.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AutoDTO {
    private Long id;
    
    @NotBlank(message = "La marca es requerida")
    private String marca;
    
    @NotBlank(message = "El modelo es requerido")
    private String modelo;
    
    @NotNull(message = "El año es requerido")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2025, message = "El año no puede ser mayor al actual")
    private Integer anio;
    
    private BigDecimal kilometraje;
    
    private String color;
    
    @NotNull(message = "El precio base es requerido")
    @DecimalMin(value = "0.0", message = "El precio base debe ser mayor a 0")
    private BigDecimal precioBase;
    
    private String descripcion;
    
    private String estado;
} 