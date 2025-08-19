package org.esfe.stayloop.controladores;

import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.servicios.interfaces.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    // Listado de hoteles con paginación + filtros
    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam(value = "zona", required = false) Integer zona,
                        @RequestParam(value = "nombre", required = false) String nombre) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // si nombre viene nulo, se lo pasamos como "" para evitar errores
        String filtroNombre = (nombre == null) ? "" : nombre;

        Page<Hotel> hoteles = hotelService.buscarPaginados(pageable, zona, filtroNombre);
        model.addAttribute("hoteles", hoteles);

        // info para el paginador
        int totalPages = hoteles.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("zona", zona);
        model.addAttribute("nombre", filtroNombre);

        return "hotel/index"; // tu dashboard de hoteles
    }

    // Vista para crear un nuevo hotel
    @GetMapping("/create")
    public String create(Hotel hotel) {
        return "hotel/create";
    }


    // Editar un hotel
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Hotel hotel = hotelService.buscarPorId(id);
        model.addAttribute("hotel", hotel);
        return "hotel/edit";
    }



}