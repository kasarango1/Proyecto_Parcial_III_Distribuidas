package ec.edu.espe.subasta.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "comprador")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Comprador extends Usuario {

    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Puja> pujas;

    public Comprador() {}

    public List<Puja> getPujas() { return pujas; }
    public void setPujas(List<Puja> pujas) { this.pujas = pujas; }
}
