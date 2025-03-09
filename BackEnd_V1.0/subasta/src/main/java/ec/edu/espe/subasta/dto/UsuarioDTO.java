package ec.edu.espe.subasta.dto;

public class UsuarioDTO {
    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String tipo;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer idUsuario, String nombre, String correo, String tipo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
