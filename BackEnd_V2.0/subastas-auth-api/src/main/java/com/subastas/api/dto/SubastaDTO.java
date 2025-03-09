package com.subastas.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubastaDTO {
    private Long id;
    
    @NotNull(message = "La fecha de inicio es requerida")
    @Future(message = "La fecha de inicio debe ser futura")
    private LocalDateTime fechaInicio;
    
    @NotNull(message = "La fecha de fin es requerida")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDateTime fechaFin;
    
    private String estado;
    
    private String descripcion;
    
    @NotEmpty(message = "Debe incluir al menos un auto en la subasta")
    private List<Long> autoIds;
} 