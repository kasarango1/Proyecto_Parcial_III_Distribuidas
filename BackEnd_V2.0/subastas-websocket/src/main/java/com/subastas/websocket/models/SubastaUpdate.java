package com.subastas.websocket.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SubastaUpdate {
    private String tipo; // NUEVA_PUJA, SUBASTA_INICIADA, SUBASTA_FINALIZADA
    private Long subastaId;
    private Long subastaAutoId;
    private BigDecimal montoActual;
    private String compradorActual;
    private LocalDateTime timestamp;
    private String mensaje;
} 