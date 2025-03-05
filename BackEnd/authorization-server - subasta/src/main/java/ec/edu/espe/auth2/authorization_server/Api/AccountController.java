package ec.edu.espe.auth2.authorization_server.Api;

import ec.edu.espe.auth2.authorization_server.Dto.UsuarioDTO;
import ec.edu.espe.auth2.authorization_server.Exception.InvalidCredentialsException;
import ec.edu.espe.auth2.authorization_server.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/authorization-server")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO loginDTO){
        try {
            return ResponseEntity.ok(this.userService.login(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().build();
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}