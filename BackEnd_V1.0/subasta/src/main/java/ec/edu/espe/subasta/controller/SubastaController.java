package ec.edu.espe.subasta.controller;

import ec.edu.espe.subasta.entity.Subasta;
import ec.edu.espe.subasta.service.SubastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/subastas")
public class SubastaController {

    @Autowired
    private SubastaService subastaService;

    @GetMapping
    public List<Subasta> listarSubastas() {
        return subastaService.listarSubastas();
    }

    @GetMapping("/{id}")
    public Subasta obtenerSubastaPorId(@PathVariable Integer id) {
        return subastaService.obtenerSubastaPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDEDOR')")
    public Subasta crearSubasta(@RequestParam LocalDateTime fechaInicio,
                                @RequestParam LocalDateTime fechaFin,
                                @RequestParam Integer vendedorId) {
        return subastaService.crearSubasta(fechaInicio, fechaFin, vendedorId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VENDEDOR') or hasRole('ADMIN')")
    public void eliminarSubasta(@PathVariable Integer id) {
        subastaService.eliminarSubasta(id);
    }
}
