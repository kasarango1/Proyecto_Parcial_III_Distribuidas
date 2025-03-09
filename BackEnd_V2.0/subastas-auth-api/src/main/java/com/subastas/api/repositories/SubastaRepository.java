package com.subastas.api.repositories;

import com.subastas.api.entities.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SubastaRepository extends JpaRepository<Subasta, Long> {
    List<Subasta> findByEstadoAndActivoTrue(Subasta.EstadoSubasta estado);
    List<Subasta> findByFechaInicioBeforeAndFechaFinAfterAndEstadoAndActivoTrue(
        LocalDateTime now,
        LocalDateTime now2,
        Subasta.EstadoSubasta estado
    );
    List<Subasta> findByFechaFinBeforeAndEstadoAndActivoTrue(
        LocalDateTime now,
        Subasta.EstadoSubasta estado
    );
} 