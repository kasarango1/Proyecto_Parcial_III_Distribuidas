package ec.edu.espe.subasta.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {
    public Administrador() {}

    public Administrador(String nombre, String correo, String contraseña) {
        super(nombre, correo, contraseña, "ADMIN");
    }
}

