package ec.edu.espe.subasta.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "vendedor")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Vendedor extends Usuario {

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Auto> autos;

    public Vendedor() {}

    public List<Auto> getAutos() { return autos; }
    public void setAutos(List<Auto> autos) { this.autos = autos; }
}
