package com.subastas.api.controllers;

import com.subastas.api.dto.PujaDTO;
import com.subastas.api.security.UserDetailsImpl;
import com.subastas.api.services.PujaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pujas")
@RequiredArgsConstructor
public class PujaController {

    private final PujaService pujaService;

    @PostMapping
    @PreAuthorize("hasRole('COMPRADOR')")
    public ResponseEntity<PujaDTO> realizarPuja(
            @Valid @RequestBody PujaDTO pujaDTO,
            Authentication authentication) {
        Long compradorId = obtenerUsuarioId(authentication);
        return ResponseEntity.ok(pujaService.realizarPuja(pujaDTO, compradorId));
    }

    @GetMapping("/subasta-auto/{subastaAutoId}")
    public ResponseEntity<List<PujaDTO>> listarPujasPorSubastaAuto(
            @PathVariable Long subastaAutoId) {
        return ResponseEntity.ok(pujaService.listarPujasPorSubastaAuto(subastaAutoId));
    }

    private Long obtenerUsuarioId(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
} 