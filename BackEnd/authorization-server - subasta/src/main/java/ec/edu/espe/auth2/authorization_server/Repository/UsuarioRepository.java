package ec.edu.espe.auth2.authorization_server.Repository;

import ec.edu.espe.auth2.authorization_server.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo(String email);
}
