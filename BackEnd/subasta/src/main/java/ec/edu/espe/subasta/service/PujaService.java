package ec.edu.espe.subasta.service;

import ec.edu.espe.subasta.entity.Puja;
import ec.edu.espe.subasta.entity.Auto;
import ec.edu.espe.subasta.entity.Comprador;
import ec.edu.espe.subasta.exception.ResourceNotFoundException;
import ec.edu.espe.subasta.repository.PujaRepository;
import ec.edu.espe.subasta.repository.AutoRepository;
import ec.edu.espe.subasta.repository.CompradorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PujaService {

    @Autowired
    private PujaRepository pujaRepository;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    public List<Puja> obtenerPujasPorAuto(Integer autoId) {
        return pujaRepository.findByAuto_IdAutoOrderByMontoDesc(autoId);
    }

    public Puja realizarPuja(Integer autoId, Integer compradorId, Double monto) {
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado con ID: " + autoId));
        Comprador comprador = (Comprador) compradorRepository.findById(compradorId)
                .orElseThrow(() -> new ResourceNotFoundException("Comprador no encontrado con ID: " + compradorId));
        Puja puja = new Puja(auto, comprador, monto);
        puja.setFecha(LocalDateTime.now());
        return pujaRepository.save(puja);
    }

    @Transactional
    public void eliminarPuja(Integer id) {
        pujaRepository.invalidarPuja(id);
    }
}
