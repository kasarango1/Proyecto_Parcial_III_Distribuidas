package ec.edu.espe.subasta.repository;

import ec.edu.espe.subasta.entity.Subasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubastaRepository extends JpaRepository<Subasta, Integer> {
    List<Subasta> findByEstadoLogico(String estado);
    @Modifying
    @Query("UPDATE Subasta s SET s.estadoLogico = 'INACTIVO' WHERE s.idSubasta = :id")
    void actualizarEstadoLogico(@Param("id") Integer id);

}
