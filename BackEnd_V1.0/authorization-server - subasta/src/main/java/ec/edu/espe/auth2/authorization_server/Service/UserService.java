package ec.edu.espe.auth2.authorization_server.Service;
import ec.edu.espe.auth2.authorization_server.Entity.Usuario;
import ec.edu.espe.auth2.authorization_server.Exception.InvalidCredentialsException;
import ec.edu.espe.auth2.authorization_server.Repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.login.AccountNotFoundException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private final UsuarioRepository usuarioRepository;

    public UserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String login(String email, String password) throws AccountNotFoundException, InvalidCredentialsException {
        Usuario usuario = this.usuarioRepository.findByCorreo(email);
        if (usuario == null) {
            throw new AccountNotFoundException("Account doesn't exist");
        }

        if (!usuario.getContraseña().equals(password)) {
            throw new InvalidCredentialsException("Wrong password");
        }

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(usuario.getTipo());
        return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(usuario.getCorreo())
                .claim("authority", simpleGrantedAuthority.getAuthority())
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(generateKeyFromSecret(), SignatureAlgorithm.HS512)
                .compact();
    }

    private SecretKey generateKeyFromSecret() {
        try {
            String secret = "CONTRASENIA-1234";
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            byte[] hash = sha.digest(secret.getBytes());
            return new SecretKeySpec(hash, "HmacSHA512");
        } catch (Exception e) {
            throw new RuntimeException("Error getting key" + e);
        }
    }
}