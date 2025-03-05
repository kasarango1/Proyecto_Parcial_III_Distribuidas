package ec.edu.espe.subasta.controller;

import ec.edu.espe.subasta.entity.Auto;
import ec.edu.espe.subasta.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @GetMapping
    public List<Auto> listarAutos() {
        return autoService.listarAutos();
    }

    @GetMapping("/{id}")
    public Auto obtenerAutoPorId(@PathVariable Integer id) {
        return autoService.obtenerAutoPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDEDOR')")
    public Auto registrarAuto(@RequestParam String marca,
                              @RequestParam String modelo,
                              @RequestParam Integer año,
                              @RequestParam Double precioBase,
                              @RequestParam Integer vendedorId) {
        return autoService.registrarAuto(marca, modelo, año, precioBase, vendedorId);
    }

    @PostMapping("/{autoId}/subasta/{subastaId}")
    @PreAuthorize("hasRole('VENDEDOR')")
    public Auto asignarAutoASubasta(@PathVariable Integer autoId,
                                    @PathVariable Integer subastaId) {
        return autoService.asignarAutoASubasta(autoId, subastaId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VENDEDOR') or hasRole('ADMIN')")
    public void eliminarAuto(@PathVariable Integer id) {
        autoService.eliminarAuto(id);
    }
}
