package com.subastas.api.repositories;

import com.subastas.api.entities.Auto;
import com.subastas.api.entities.Subasta;
import com.subastas.api.entities.SubastaAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubastaAutoRepository extends JpaRepository<SubastaAuto, Long> {
    List<SubastaAuto> findBySubasta(Subasta subasta);
    List<SubastaAuto> findByAuto(Auto auto);
    Optional<SubastaAuto> findBySubastaAndAuto(Subasta subasta, Auto auto);
    List<SubastaAuto> findByEstado(SubastaAuto.EstadoSubastaAuto estado);
} 