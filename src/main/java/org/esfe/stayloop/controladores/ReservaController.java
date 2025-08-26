package org.esfe.stayloop.controladores;

import jakarta.validation.Valid;
import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.modelos.Imagen;
import org.esfe.stayloop.modelos.Reserva;
import org.esfe.stayloop.modelos.TipoHabitacion;
import org.esfe.stayloop.servicios.implementaciones.ImagenService;
import org.esfe.stayloop.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.esfe.stayloop.modelos.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @Autowired
    private IImagenService imagenService;

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

    @GetMapping("/create")
    public String createReservaForm(
            @RequestParam(value = "idHotel", required = false) Integer idHotel,
            @RequestParam(value = "idTipoHabitacion", required = false) Integer idTipoHabitacion,
            Model model,
            Reserva reserva) {

        // Hotel fijo
        if (idHotel != null) {
            Hotel hotel = hotelService.buscarPorId(idHotel);
            if (hotel != null) {
                model.addAttribute("hotelActual", hotel);
                reserva.setIdHotel(hotel.getId());
            }
        }

        // Tipo de habitación fijo
        if (idTipoHabitacion != null) {
            TipoHabitacion tipo = tipoHabitacionService.buscarPorId(idTipoHabitacion);
            if (tipo != null) {
                model.addAttribute("tipoHabitacionActual", tipo);
                reserva.setIdTipoHabitacion(tipo.getId());
            }
        }

        // Usuario logueado fijo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Spring Security guarda aquí el username o email

        usuarioService.buscarPorEmail(email).ifPresent(usuarioLogueado -> {
            model.addAttribute("usuarioActual", usuarioLogueado);
            reserva.setIdUsuario(usuarioLogueado.getId());
        });

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

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Reserva reserva = reservaService.buscarPorId(id);
        model.addAttribute("reserva", reserva);

        model.addAttribute("hoteles", hotelService.obtenerTodos());
        model.addAttribute("tiposHabitacion", tipoHabitacionService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());

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


    @GetMapping("/hoteles")
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


        // info para el paginador
        int totalPages = hoteles.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }



        model.addAttribute("hoteles", hoteles);
        model.addAttribute("zona", zona);
        model.addAttribute("nombre", filtroNombre);

        return "reserva/hotelesusuarios"; // tu dashboard de hoteles
    }


    @GetMapping("/detallehotel/{id}")
    public String detallehotel(@PathVariable("id") Integer id, Model model) {
        Reserva reserva = new Reserva();
        model.addAttribute("reserva", reserva);
        List<Imagen>Imagenes= imagenService.buscarPorIdHotel(id);
        List<TipoHabitacion>Habitaciones= tipoHabitacionService.buscarPorIdHotel(id);
        Hotel hotel = hotelService.buscarPorId(id);

        model.addAttribute("Imagenes", Imagenes);
        model.addAttribute("Hotel", hotel);
        model.addAttribute("Habitaciones", Habitaciones);

        model.addAttribute("hoteles", hotelService.obtenerTodos());
        model.addAttribute("tiposHabitacion", tipoHabitacionService.obtenerTodos());
        model.addAttribute("usuarios", usuarioService.obtenerTodos());

        return "reserva/detallehotel";
    }

    @GetMapping("/imagenesHotel/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable("id") Integer id){
        return imagenService.obtenerPorId(id)
                .map(Imagen::getImagen)
                .orElse(null);
    }





}
