package com.subastas.api.services;

import com.subastas.api.dto.*;
import com.subastas.api.entities.*;
import com.subastas.api.exceptions.*;
import com.subastas.api.repositories.*;
import com.subastas.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final VendedorRepository vendedorRepository;
    private final CompradorRepository compradorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public JwtResponseDTO registrarVendedor(RegistroVendedorDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        if (vendedorRepository.existsByRuc(dto.getRuc())) {
            throw new BadRequestException("El RUC ya está registrado");
        }

        Vendedor vendedor = new Vendedor();
        vendedor.setEmail(dto.getEmail());
        vendedor.setPassword(passwordEncoder.encode(dto.getPassword()));
        vendedor.setNombre(dto.getNombre());
        vendedor.setApellido(dto.getApellido());
        vendedor.setTelefono(dto.getTelefono());
        vendedor.setRuc(dto.getRuc());
        vendedor.setDireccionNegocio(dto.getDireccionNegocio());

        vendedor = vendedorRepository.save(vendedor);

        String token = jwtTokenProvider.generateToken(vendedor.getEmail(), vendedor.getId());
        return new JwtResponseDTO(token, "Bearer", vendedor.getId(),
                vendedor.getEmail(), vendedor.getNombre(), "VENDEDOR");
    }

    @Transactional
    public JwtResponseDTO registrarComprador(RegistroCompradorDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        if (compradorRepository.existsByDni(dto.getDni())) {
            throw new BadRequestException("El DNI ya está registrado");
        }

        Comprador comprador = new Comprador();
        comprador.setEmail(dto.getEmail());
        comprador.setPassword(passwordEncoder.encode(dto.getPassword()));
        comprador.setNombre(dto.getNombre());
        comprador.setApellido(dto.getApellido());
        comprador.setTelefono(dto.getTelefono());
        comprador.setDni(dto.getDni());
        comprador.setDireccionEnvio(dto.getDireccionEnvio());

        comprador = compradorRepository.save(comprador);

        String token = jwtTokenProvider.generateToken(comprador.getEmail(), comprador.getId());
        return new JwtResponseDTO(token, "Bearer", comprador.getId(),
                comprador.getEmail(), comprador.getNombre(), "COMPRADOR");
    }

    public JwtResponseDTO login(LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        String token = jwtTokenProvider.generateToken(usuario.getEmail(), usuario.getId());
        return new JwtResponseDTO(token, "Bearer", usuario.getId(),
                usuario.getEmail(), usuario.getNombre(), usuario.getTipoUsuario().toString());
    }
}