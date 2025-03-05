package ec.edu.espe.subasta.controller;

import ec.edu.espe.subasta.entity.Puja;
import ec.edu.espe.subasta.service.PujaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pujas")
public class PujaController {

    @Autowired
    private PujaService pujaService;

    @GetMapping("/auto/{autoId}")
    public List<Puja> obtenerPujasPorAuto(@PathVariable Integer autoId) {
        return pujaService.obtenerPujasPorAuto(autoId);
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPRADOR')")
    public Puja realizarPuja(@RequestParam Integer autoId,
                             @RequestParam Integer compradorId,
                             @RequestParam Double monto) {
        return pujaService.realizarPuja(autoId, compradorId, monto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarPuja(@PathVariable Integer id) {
        pujaService.eliminarPuja(id);
    }
}
