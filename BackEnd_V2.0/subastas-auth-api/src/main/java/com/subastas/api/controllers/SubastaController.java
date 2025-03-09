package com.subastas.api.controllers;

import com.subastas.api.dto.SubastaDTO;
import com.subastas.api.security.UserDetailsImpl;
import com.subastas.api.services.SubastaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subastas")
@RequiredArgsConstructor
public class SubastaController {

    private final SubastaService subastaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<SubastaDTO> crearSubasta(
            @Valid @RequestBody SubastaDTO subastaDTO,
            Authentication authentication) {
        Long adminId = obtenerUsuarioId(authentication);
        return ResponseEntity.ok(subastaService.crearSubasta(subastaDTO, adminId));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<SubastaDTO>> listarSubastasActivas() {
        return ResponseEntity.ok(subastaService.listarSubastasActivas());
    }

    private Long obtenerUsuarioId(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
} 