package com.subastas.api.repositories;

import com.subastas.api.entities.Auto;
import com.subastas.api.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutoRepository extends JpaRepository<Auto, Long> {
    List<Auto> findByVendedorAndActivoTrue(Vendedor vendedor);
    List<Auto> findByEstadoAndActivoTrue(Auto.EstadoAuto estado);
    List<Auto> findByActivoTrue();
} 