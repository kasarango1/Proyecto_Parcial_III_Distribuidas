package ec.edu.espe.subasta.repository;

import ec.edu.espe.subasta.entity.Puja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PujaRepository extends JpaRepository<Puja, Integer> {
    List<Puja> findByAuto_IdAutoOrderByMontoDesc(Integer autoId);
    Optional<Puja> findTopByAuto_IdAutoOrderByMontoDesc(Integer autoId);

    @Modifying
    @Query("UPDATE Puja p SET p.estado = 'INVALIDADA' WHERE p.idPuja = :id")
    void invalidarPuja(@Param("id") Integer id);

}
