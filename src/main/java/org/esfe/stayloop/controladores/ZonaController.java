package org.esfe.stayloop.controladores;

import jakarta.validation.Valid;
import org.esfe.stayloop.modelos.Zona;
import org.esfe.stayloop.servicios.interfaces.IZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/zonas")
public class ZonaController {

    @Autowired
    private IZonaService zonaService;

    // LISTAR ZONAS
    @GetMapping
    public String getZonasPaginadas(
            Model model,
            @RequestParam(value = "page", required = false) Optional<Integer> page,
            @RequestParam(value = "size", required = false) Optional<Integer> size,
            @RequestParam(value = "nombre", required = false) Optional<String> nombre
    ) {
        int currentPage = page.orElse(1) - 1; // Pageable empieza en 0
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        String filtroNombre = nombre.orElse("");

        Page<Zona> zonas = zonaService.buscarPaginados(pageable, filtroNombre);

        int totalPages = zonas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("zonas", zonas);
        model.addAttribute("nombre", filtroNombre);

        return "zona/index";
    }

    // FORMULARIO CREAR
    @GetMapping("/create")
    public String createZonaForm(Model model, Zona zona) {
        model.addAttribute("zona", zona);
        return "zona/create";
    }

    // GUARDAR NUEVA ZONA
    @PostMapping("/create")
    public String createZona(Zona zona, Model model) {
        zonaService.crearOEditar(zona);
        return "redirect:/zonas";
    }

    // DETALLES
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Zona zona = zonaService.buscarPorId(id);
        model.addAttribute("zona", zona);
        return "zona/details";
    }

    // FORMULARIO EDITAR
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Zona zona = zonaService.buscarPorId(id);
        model.addAttribute("zona", zona);
        return "zona/edit";
    }

    // GUARDAR (EDITAR o CREAR)
    @PostMapping("/save")
    public String saveZona(
            @Valid @ModelAttribute("zona") Zona zona,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("zona", zona);
            return "zona/edit";
        }

        zonaService.crearOEditar(zona);

        return "redirect:/zonas"; // Siempre redirige a la lista de zonas
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Zona zona = zonaService.buscarPorId(id);  // Obtener la zona por ID
        model.addAttribute("zona", zona);
        return "zona/delete";
    }

    // Procesar la eliminación
    @PostMapping("/delete")
    public String remove(@RequestParam("id") Integer id) {
        zonaService.eliminarPorId(id);
        return "redirect:/zonas";
    }
}
