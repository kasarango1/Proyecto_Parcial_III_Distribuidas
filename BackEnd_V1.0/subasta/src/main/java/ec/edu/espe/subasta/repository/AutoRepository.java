package ec.edu.espe.subasta.repository;

import ec.edu.espe.subasta.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    List<Auto> findBySubasta_IdSubasta(Integer subastaId);

    List<Auto> findByVendedor_IdUsuario(Integer vendedorId);

    @Modifying
    @Query("UPDATE Auto a SET a.estadoLogico = 'INACTIVO' WHERE a.idAuto = :id")
    void actualizarEstadoLogico(@Param("id") Integer id);

}
