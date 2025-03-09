package com.subastas.api.services;

import com.subastas.api.dto.PujaDTO;
import com.subastas.api.entities.*;
import com.subastas.api.exceptions.BadRequestException;
import com.subastas.api.exceptions.ResourceNotFoundException;
import com.subastas.api.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PujaService {
    
    private final PujaRepository pujaRepository;
    private final SubastaAutoRepository subastaAutoRepository;
    private final CompradorRepository compradorRepository;

    @Transactional
    public PujaDTO realizarPuja(PujaDTO dto, Long compradorId) {
        Comprador comprador = compradorRepository.findById(compradorId)
            .orElseThrow(() -> new ResourceNotFoundException("Comprador no encontrado"));

        SubastaAuto subastaAuto = subastaAutoRepository.findById(dto.getSubastaAutoId())
            .orElseThrow(() -> new ResourceNotFoundException("Subasta-Auto no encontrado"));

        validarPuja(dto, subastaAuto, comprador);

        Puja puja = new Puja();
        puja.setSubastaAuto(subastaAuto);
        puja.setComprador(comprador);
        puja.setMonto(dto.getMonto());
        
        puja = pujaRepository.save(puja);
        return convertToDTO(puja);
    }

    public List<PujaDTO> listarPujasPorSubastaAuto(Long subastaAutoId) {
        SubastaAuto subastaAuto = subastaAutoRepository.findById(subastaAutoId)
            .orElseThrow(() -> new ResourceNotFoundException("Subasta-Auto no encontrado"));

        return pujaRepository.findBySubastaAutoOrderByMontoDesc(subastaAuto).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private void validarPuja(PujaDTO dto, SubastaAuto subastaAuto, Comprador comprador) {
        if (subastaAuto.getSubasta().getEstado() != Subasta.EstadoSubasta.ACTIVA) {
            throw new BadRequestException("La subasta no está activa");
        }

        if (LocalDateTime.now().isBefore(subastaAuto.getSubasta().getFechaInicio()) ||
            LocalDateTime.now().isAfter(subastaAuto.getSubasta().getFechaFin())) {
            throw new BadRequestException("La subasta no está en su periodo de actividad");
        }

        if (subastaAuto.getAuto().getVendedor().getId().equals(comprador.getId())) {
            throw new BadRequestException("No puedes pujar por tu propio auto");
        }

        BigDecimal ultimaPuja = pujaRepository.findHighestBidForSubastaAuto(subastaAuto)
            .map(Puja::getMonto)
            .orElse(subastaAuto.getAuto().getPrecioBase());

        if (dto.getMonto().compareTo(ultimaPuja) <= 0) {
            throw new BadRequestException("El monto de la puja debe ser mayor a la última puja o al precio base");
        }
    }

    private PujaDTO convertToDTO(Puja puja) {
        PujaDTO dto = new PujaDTO();
        dto.setId(puja.getId());
        dto.setSubastaAutoId(puja.getSubastaAuto().getId());
        dto.setMonto(puja.getMonto());
        dto.setEstado(puja.getEstado().toString());
        return dto;
    }
} 