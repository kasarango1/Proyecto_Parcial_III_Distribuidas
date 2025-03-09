package com.subastas.api.controllers;

import com.subastas.api.dto.AutoDTO;
import com.subastas.api.security.UserDetailsImpl;
import com.subastas.api.services.AutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
@RequiredArgsConstructor
public class AutoController {

    private final AutoService autoService;

    @PostMapping
    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<AutoDTO> crearAuto(
            @Valid @RequestBody AutoDTO autoDTO,
            Authentication authentication) {
        Long vendedorId = obtenerUsuarioId(authentication);
        return ResponseEntity.ok(autoService.crearAuto(autoDTO, vendedorId));
    }

    @GetMapping("/vendedor")
    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<List<AutoDTO>> listarAutosPorVendedor(Authentication authentication) {
        Long vendedorId = obtenerUsuarioId(authentication);
        return ResponseEntity.ok(autoService.listarAutosPorVendedor(vendedorId));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<AutoDTO>> listarAutosDisponibles() {
        return ResponseEntity.ok(autoService.listarAutosDisponibles());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<AutoDTO> actualizarAuto(
            @PathVariable Long id,
            @Valid @RequestBody AutoDTO autoDTO,
            Authentication authentication) {
        Long vendedorId = obtenerUsuarioId(authentication);
        return ResponseEntity.ok(autoService.actualizarAuto(id, autoDTO, vendedorId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<Void> eliminarAuto(
            @PathVariable Long id,
            Authentication authentication) {
        Long vendedorId = obtenerUsuarioId(authentication);
        autoService.eliminarAuto(id, vendedorId);
        return ResponseEntity.noContent().build();
    }

    private Long obtenerUsuarioId(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
} 