package com.subastas.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PujaDTO {
    private Long id;
    
    @NotNull(message = "El ID de la subasta-auto es requerido")
    private Long subastaAutoId;
    
    @NotNull(message = "El monto de la puja es requerido")
    @DecimalMin(value = "0.0", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;
    
    private String estado;
} 