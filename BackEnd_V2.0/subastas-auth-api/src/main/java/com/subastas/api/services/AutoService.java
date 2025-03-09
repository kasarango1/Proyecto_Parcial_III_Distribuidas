package com.subastas.api.services;

import com.subastas.api.dto.AutoDTO;
import com.subastas.api.entities.Auto;
import com.subastas.api.entities.Vendedor;
import com.subastas.api.exceptions.BadRequestException;
import com.subastas.api.exceptions.ResourceNotFoundException;
import com.subastas.api.repositories.AutoRepository;
import com.subastas.api.repositories.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoService {
    
    private final AutoRepository autoRepository;
    private final VendedorRepository vendedorRepository;

    @Transactional
    public AutoDTO crearAuto(AutoDTO dto, Long vendedorId) {
        Vendedor vendedor = vendedorRepository.findById(vendedorId)
            .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado"));

        Auto auto = new Auto();
        auto.setVendedor(vendedor);
        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setAnio(dto.getAnio());
        auto.setKilometraje(dto.getKilometraje());
        auto.setColor(dto.getColor());
        auto.setPrecioBase(dto.getPrecioBase());
        auto.setDescripcion(dto.getDescripcion());
        
        auto = autoRepository.save(auto);
        return convertToDTO(auto);
    }

    public List<AutoDTO> listarAutosPorVendedor(Long vendedorId) {
        Vendedor vendedor = vendedorRepository.findById(vendedorId)
            .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado"));

        return autoRepository.findByVendedorAndActivoTrue(vendedor).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<AutoDTO> listarAutosDisponibles() {
        return autoRepository.findByEstadoAndActivoTrue(Auto.EstadoAuto.DISPONIBLE).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public AutoDTO actualizarAuto(Long id, AutoDTO dto, Long vendedorId) {
        Auto auto = autoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado"));

        if (!auto.getVendedor().getId().equals(vendedorId)) {
            throw new BadRequestException("No tienes permiso para modificar este auto");
        }

        if (auto.getEstado() != Auto.EstadoAuto.DISPONIBLE) {
            throw new BadRequestException("No se puede modificar un auto que está en subasta o vendido");
        }

        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setAnio(dto.getAnio());
        auto.setKilometraje(dto.getKilometraje());
        auto.setColor(dto.getColor());
        auto.setPrecioBase(dto.getPrecioBase());
        auto.setDescripcion(dto.getDescripcion());
        
        auto = autoRepository.save(auto);
        return convertToDTO(auto);
    }

    @Transactional
    public void eliminarAuto(Long id, Long vendedorId) {
        Auto auto = autoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado"));

        if (!auto.getVendedor().getId().equals(vendedorId)) {
            throw new BadRequestException("No tienes permiso para eliminar este auto");
        }

        if (auto.getEstado() != Auto.EstadoAuto.DISPONIBLE) {
            throw new BadRequestException("No se puede eliminar un auto que está en subasta o vendido");
        }

        auto.setActivo(false);
        autoRepository.save(auto);
    }

    private AutoDTO convertToDTO(Auto auto) {
        AutoDTO dto = new AutoDTO();
        dto.setId(auto.getId());
        dto.setMarca(auto.getMarca());
        dto.setModelo(auto.getModelo());
        dto.setAnio(auto.getAnio());
        dto.setKilometraje(auto.getKilometraje());
        dto.setColor(auto.getColor());
        dto.setPrecioBase(auto.getPrecioBase());
        dto.setDescripcion(auto.getDescripcion());
        dto.setEstado(auto.getEstado().toString());
        return dto;
    }
} 