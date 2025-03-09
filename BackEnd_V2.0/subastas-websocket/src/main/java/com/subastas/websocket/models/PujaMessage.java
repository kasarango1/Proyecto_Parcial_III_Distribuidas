package com.subastas.websocket.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PujaMessage {
    @NotNull
    private Long subastaAutoId;
    
    @NotNull
    private Long compradorId;
    
    @NotNull
    @Positive
    private BigDecimal monto;
    
    private String compradorNombre;
} 