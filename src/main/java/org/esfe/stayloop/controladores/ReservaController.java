package org.esfe.stayloop.controladores;

import jakarta.validation.Valid;
import org.esfe.stayloop.modelos.Reserva;
import org.esfe.stayloop.servicios.interfaces.IHotelService;
import org.esfe.stayloop.servicios.interfaces.IReservaService;
import org.esfe.stayloop.servicios.interfaces.ITipoHabitacionService;
import org.esfe.stayloop.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private ITipoHabitacionService tipoHabitacionService;

    @Autowired
    private IUsuarioService usuarioService;

    // LISTAR RESERVAS PAGINADAS
    @GetMapping
    public String getReservasPaginadas(
            Model model,
            @RequestParam(value = "page", required = false) Optional<Integer> page,
            @RequestParam(value = "size", required = false) Optional<Integer> size,
            @RequestParam(value = "idUsuario", required = false) Optional<Integer> idUsuario,
            @RequestParam(value = "idHotel", required = false) Optional<Integer> idHotel,
            @RequestParam(value = "totalMin", required = false) Optional<BigDecimal> totalMin
    ) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(Sort.Direction.DESC, "id"));


        Integer idUsuarioValue = idUsuario.orElse(null);
        Integer idHotelValue = idHotel.orElse(null);
        BigDecimal totalMinValue = totalMin.orElse(null);

        Page<Reserva> reservas = reservaService.buscarPaginados(
                idUsuarioValue,
                idHotelValue,
                totalMinValue,
                pageable
        );

        int totalPages = reservas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("hoteles", hotelService.obtenerTodos());
        model.addAttribute("tiposHabitacion", tipoHabitacionService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        model.addAttribute("reservas", reservas);
        model.addAttribute("totalMin", totalMinValue);

        return "reserva/index";
    }

    // FORMULARIO CREAR RESERVA
    @GetMapping("/create")
    public String createReservaForm(Model model, Reserva reserva) {
        model.addAttribute("reserva", reserva);
        model.addAttribute("hoteles", hotelService.obtenerTodos());
        model.addAttribute("tiposHabitacion", tipoHabitacionService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "reserva/create";
    }

    // GUARDAR NUEVA RESERVA
    @PostMapping("/save")
    public String saveReserva(@Valid @ModelAttribute("reserva") Reserva reserva,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hoteles", hotelService.obtenerTodos());
            model.addAttribute("tiposHabitacion", tipoHabitacionService.obtenerTodos());
            model.addAttribute("usuarios", usuarioService.obtenerTodos());
            return "reserva/create";
        }

        // Si quieres, puedes asignar el usuario logueado automáticamente
        // Usuario usuarioLogueado = ...;
        // reserva.setUsuario(usuarioLogueado);


        reservaService.crearOEditar(reserva);
        return "redirect:/reservas";
    }

    // FORMULARIO EDITAR
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Reserva reserva = reservaService.buscarPorId(id);
        model.addAttribute("reserva", reserva);
        model.addAttribute("hoteles", hotelService.obtenerTodos());
        model.addAttribute("tiposHabitacion", tipoHabitacionService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());
        return "reserva/edit";
    }

    // DETALLES
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Reserva reserva = reservaService.buscarPorId(id);
        model.addAttribute("reserva", reserva);
        return "reserva/details";
    }

    // ELIMINAR
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Reserva reserva = reservaService.buscarPorId(id);
        model.addAttribute("reserva", reserva);
        return "reserva/delete";
    }

    @PostMapping("/delete")
    public String remove(@RequestParam("id") Integer id) {
        reservaService.eliminarPorId(id);
        return "redirect:/reservas";
    }
}
