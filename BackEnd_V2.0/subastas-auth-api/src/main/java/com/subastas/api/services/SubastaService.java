package com.subastas.api.services;

import com.subastas.api.dto.SubastaDTO;
import com.subastas.api.entities.*;
import com.subastas.api.exceptions.BadRequestException;
import com.subastas.api.exceptions.ResourceNotFoundException;
import com.subastas.api.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubastaService {

    private final SubastaRepository subastaRepository;
    private final AutoRepository autoRepository;
    private final SubastaAutoRepository subastaAutoRepository;
    private final AdministradorRepository administradorRepository;
    private final PujaRepository pujaRepository;

    @Transactional
    public SubastaDTO crearSubasta(SubastaDTO dto, Long adminId) {
        validarFechasSubasta(dto);

        Administrador admin = administradorRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado"));

        Subasta subasta = new Subasta();
        subasta.setFechaInicio(dto.getFechaInicio());
        subasta.setFechaFin(dto.getFechaFin());
        subasta.setDescripcion(dto.getDescripcion());
        subasta.setCreadoPor(admin);

        subasta = subastaRepository.save(subasta);

        for (Long autoId : dto.getAutoIds()) {
            Auto auto = autoRepository.findById(autoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado: " + autoId));

            if (auto.getEstado() != Auto.EstadoAuto.DISPONIBLE) {
                throw new BadRequestException("El auto " + autoId + " no está disponible para subasta");
            }

            SubastaAuto subastaAuto = new SubastaAuto();
            subastaAuto.setSubasta(subasta);
            subastaAuto.setAuto(auto);
            subastaAutoRepository.save(subastaAuto);

            auto.setEstado(Auto.EstadoAuto.EN_SUBASTA);
            autoRepository.save(auto);
        }

        return convertToDTO(subasta);
    }

    public List<SubastaDTO> listarSubastasActivas() {
        return subastaRepository.findByEstadoAndActivoTrue(Subasta.EstadoSubasta.ACTIVA).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 60000) // Ejecutar cada minuto
    @Transactional
    public void actualizarEstadoSubastas() {
        LocalDateTime now = LocalDateTime.now();

        // Activar subastas pendientes que deben iniciar
        List<Subasta> subastasPendientes = subastaRepository.findByFechaInicioBeforeAndFechaFinAfterAndEstadoAndActivoTrue(
                now, now, Subasta.EstadoSubasta.PENDIENTE);

        for (Subasta subasta : subastasPendientes) {
            subasta.setEstado(Subasta.EstadoSubasta.ACTIVA);
            subastaRepository.save(subasta);
        }

        // Finalizar subastas que han terminado
        List<Subasta> subastasActivas = subastaRepository.findByFechaFinBeforeAndEstadoAndActivoTrue(
                now, Subasta.EstadoSubasta.ACTIVA);

        for (Subasta subasta : subastasActivas) {
            finalizarSubasta(subasta);
        }
    }

    @Transactional
    protected void finalizarSubasta(Subasta subasta) {
        subasta.setEstado(Subasta.EstadoSubasta.FINALIZADA);

        for (SubastaAuto subastaAuto : subasta.getAutosEnSubasta()) {
            pujaRepository.findHighestBidForSubastaAuto(subastaAuto).ifPresentOrElse(
                    puja -> {
                        subastaAuto.setEstado(SubastaAuto.EstadoSubastaAuto.VENDIDO);
                        subastaAuto.setPrecioFinal(puja.getMonto());
                        puja.setEstado(Puja.EstadoPuja.GANADORA);
                        subastaAuto.getAuto().setEstado(Auto.EstadoAuto.VENDIDO);
                    },
                    () -> {
                        subastaAuto.setEstado(SubastaAuto.EstadoSubastaAuto.NO_VENDIDO);
                        subastaAuto.getAuto().setEstado(Auto.EstadoAuto.DISPONIBLE);
                    }
            );
        }

        subastaRepository.save(subasta);
    }

    private void validarFechasSubasta(SubastaDTO dto) {
        LocalDateTime now = LocalDateTime.now();

        if (dto.getFechaInicio().isBefore(now)) {
            throw new BadRequestException("La fecha de inicio debe ser futura");
        }

        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        if (dto.getFechaFin().isBefore(dto.getFechaInicio().plusHours(1))) {
            throw new BadRequestException("La subasta debe durar al menos 1 hora");
        }
    }

    private SubastaDTO convertToDTO(Subasta subasta) {
        SubastaDTO dto = new SubastaDTO();
        dto.setId(subasta.getId());
        dto.setFechaInicio(subasta.getFechaInicio());
        dto.setFechaFin(subasta.getFechaFin());
        dto.setEstado(subasta.getEstado().toString());
        dto.setDescripcion(subasta.getDescripcion());
        dto.setAutoIds(subasta.getAutosEnSubasta().stream()
                .map(sa -> sa.getAuto().getId())
                .collect(Collectors.toList()));
        return dto;
    }
}