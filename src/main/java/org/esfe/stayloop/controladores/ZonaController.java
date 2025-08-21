package org.esfe.stayloop.controladores;

import org.esfe.stayloop.modelos.Rol;
import org.esfe.stayloop.modelos.Usuario;
import org.esfe.stayloop.modelos.Zona;
import org.esfe.stayloop.servicios.interfaces.IZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zonas")
public class ZonaController {

    @Autowired
    private IZonaService zonaService;


    // Obtener zonas paginadas y filtradas por nombre
    @GetMapping
    public Page<Zona> getZonasPaginadas(Pageable pageable,
                                        @RequestParam(required = false, defaultValue = "") String nombre) {
        return zonaService.buscarPaginados(pageable, nombre);
    }


    // Obtener una zona por ID
    @GetMapping("/create")
    public ResponseEntity<Zona> getZonaById(@PathVariable Integer id) {
        Zona zona = zonaService.buscarPorId(id);
        return zona != null ? ResponseEntity.ok(zona) : ResponseEntity.notFound().build();
    }

    // Crear nueva zona
    @PostMapping("/save")
    public Zona createZona(@RequestBody Zona zona) {
        return zonaService.crearOEditar(zona);
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Zona zona = zonaService.buscarPorId(id);
        model.addAttribute("zona", zona);
        return "zona/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Zona zona = zonaService.buscarPorId(id);
        model.addAttribute("zona", zona);
        return "zona/edit";  //carpeta y vista
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model){
        Zona zona = zonaService.buscarPorId(id);
        model.addAttribute("zona", zona);
        return "zona/delete";
    }
    
    // Eliminar una zona
    @DeleteMapping
    public String deleteZona(Zona zona) {
        zonaService.eliminarPorId(zona.getId());
        return "redirect:/usuarioControl";
    }
}
