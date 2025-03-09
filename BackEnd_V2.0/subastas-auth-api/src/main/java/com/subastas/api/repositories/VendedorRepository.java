package com.subastas.api.repositories;

import com.subastas.api.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findByEmail(String email);
    boolean existsByRuc(String ruc);
} 