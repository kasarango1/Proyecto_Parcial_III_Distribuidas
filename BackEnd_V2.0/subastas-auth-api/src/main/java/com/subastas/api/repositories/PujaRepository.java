package com.subastas.api.repositories;

import com.subastas.api.entities.Comprador;
import com.subastas.api.entities.Puja;
import com.subastas.api.entities.SubastaAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PujaRepository extends JpaRepository<Puja, Long> {
    List<Puja> findBySubastaAutoOrderByMontoDesc(SubastaAuto subastaAuto);
    List<Puja> findByComprador(Comprador comprador);
    
    @Query("SELECT p FROM Puja p WHERE p.subastaAuto = ?1 AND p.monto = (SELECT MAX(p2.monto) FROM Puja p2 WHERE p2.subastaAuto = ?1)")
    Optional<Puja> findHighestBidForSubastaAuto(SubastaAuto subastaAuto);
} 