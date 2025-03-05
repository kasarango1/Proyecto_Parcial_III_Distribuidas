package ec.edu.espe.subasta.service;

import ec.edu.espe.subasta.entity.Auto;
import ec.edu.espe.subasta.entity.Subasta;
import ec.edu.espe.subasta.entity.Vendedor;
import ec.edu.espe.subasta.exception.ResourceNotFoundException;
import ec.edu.espe.subasta.repository.AutoRepository;
import ec.edu.espe.subasta.repository.SubastaRepository;
import ec.edu.espe.subasta.repository.VendedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private SubastaRepository subastaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Auto> listarAutos() {
        return autoRepository.findAll();
    }

    public List<Auto> listarAutosPorSubasta(Integer subastaId) {
        return autoRepository.findBySubasta_IdSubasta(subastaId);

    }

    public Auto obtenerAutoPorId(Integer id) {
        return autoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado con ID: " + id));
    }

    public Auto registrarAuto(String marca, String modelo, Integer año, Double precioBase, Integer vendedorId) {
        Vendedor vendedor = (Vendedor) vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + vendedorId));
        Auto auto = new Auto(marca, modelo, año, precioBase, vendedor);
        return autoRepository.save(auto);
    }

    public Auto asignarAutoASubasta(Integer autoId, Integer subastaId) {
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado con ID: " + autoId));
        Subasta subasta = subastaRepository.findById(subastaId)
                .orElseThrow(() -> new ResourceNotFoundException("Subasta no encontrada con ID: " + subastaId));
        auto.setSubasta(subasta);
        return autoRepository.save(auto);
    }

    @Transactional
    public void eliminarAuto(Integer id) {
        autoRepository.actualizarEstadoLogico(id);
    }
}
