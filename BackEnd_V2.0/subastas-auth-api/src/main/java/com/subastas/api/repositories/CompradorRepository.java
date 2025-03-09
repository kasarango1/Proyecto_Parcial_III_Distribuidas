package com.subastas.api.repositories;

import com.subastas.api.entities.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompradorRepository extends JpaRepository<Comprador, Long> {
    Optional<Comprador> findByEmail(String email);
    boolean existsByDni(String dni);
} 