package ec.edu.espe.subasta.repository;

import ec.edu.espe.subasta.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByTipo(String tipo); // Buscar usuarios por tipo (COMPRADOR, VENDEDOR, ADMIN)

    @Modifying
    @Query("UPDATE Usuario u SET u.estadoLogico = 'INACTIVO' WHERE u.idUsuario = :id")
    void actualizarEstadoLogico(@Param("id") Integer id);

}
