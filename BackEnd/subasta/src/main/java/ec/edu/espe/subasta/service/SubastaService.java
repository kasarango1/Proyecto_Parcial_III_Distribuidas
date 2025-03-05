package ec.edu.espe.subasta.service;

import ec.edu.espe.subasta.entity.Subasta;
import ec.edu.espe.subasta.entity.Vendedor;
import ec.edu.espe.subasta.exception.ResourceNotFoundException;
import ec.edu.espe.subasta.repository.SubastaRepository;
import ec.edu.espe.subasta.repository.VendedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubastaService {

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Subasta> listarSubastas() {
        return subastaRepository.findByEstadoLogico("ACTIVO");
    }

    public Subasta obtenerSubastaPorId(Integer id) {
        return subastaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subasta no encontrada con ID: " + id));
    }

    public Subasta crearSubasta(LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer vendedorId) {
        Vendedor vendedor = (Vendedor) vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + vendedorId));
        Subasta subasta = new Subasta(fechaInicio, fechaFin, vendedor);
        return subastaRepository.save(subasta);
    }

    @Transactional
    public void eliminarSubasta(Integer id) {
        subastaRepository.actualizarEstadoLogico(id);
    }
}
