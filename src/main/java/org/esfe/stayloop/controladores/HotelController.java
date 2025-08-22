package org.esfe.stayloop.controladores;

import jakarta.validation.Valid;
import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.modelos.Imagen;
import org.esfe.stayloop.modelos.TipoHabitacion;
import org.esfe.stayloop.modelos.Zona;
import org.esfe.stayloop.servicios.interfaces.IHotelService;
import org.esfe.stayloop.servicios.interfaces.IImagenService;
import org.esfe.stayloop.servicios.interfaces.ITipoHabitacionService;
import org.esfe.stayloop.servicios.interfaces.IZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IZonaService zonaService;

    @Autowired
    private ITipoHabitacionService tipoHabitacionService;

    @Autowired
    private IImagenService iImagenService;

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
    public String create(Model model) {
        Hotel hotel = new Hotel();

        hotel.getTiposHabitacion().add(new TipoHabitacion());
        List<Zona> zonas = zonaService.obtenerTodos();

        model.addAttribute("hotelForm", hotel);
        model.addAttribute("zonas", zonas);
        return "hotel/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam("idZona")Integer idZona,
                       @RequestParam("images") List<MultipartFile> images,
                       @RequestParam("authCheck") String authCheck,
                       @Valid @ModelAttribute("hotelForm") Hotel hotel,
                       BindingResult bindingResult, Model model){

                // Validaciones
        if (Objects.equals(authCheck, "authCreate")) {
            if (hotel.getTiposHabitacion() == null || hotel.getTiposHabitacion().isEmpty()) {
                bindingResult.rejectValue("tiposHabitacion", "error.NotNull", "Ingrese al menos un tipo de hospedaje");
            }
            if (images == null || images.isEmpty()) {
                bindingResult.rejectValue("imagenes", "error.NotNull", "Ingrese al menos una imagen");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("hotelForm", hotel);
            List<Zona> zonas = zonaService.obtenerTodos();
            model.addAttribute("zonas", zonas);
            return Objects.equals(authCheck, "authCreate") ? "hotel/create" : "hotel/edit";
        }

        try {
            // Guardar o actualizar el hotel
            Hotel hotelSaved = hotelService.crearOEditar(hotel);
            Integer hotelId = Objects.equals(authCheck, "authCreate") ? hotelSaved.getId() : hotel.getId();

            // Procesar tipos de habitación
            if (hotel.getTiposHabitacion() != null) {
                for (TipoHabitacion tipo : hotel.getTiposHabitacion()) {
                    if (tipo.getId() == null) {
                        tipo.setIdHotel(hotelId);
                        tipoHabitacionService.crearOEditar(tipo);
                    }
                }
            }

            // Eliminar tipos de habitación marcados
            if (hotel.getIdsTiposHabitacionParaEliminar() != null && !hotel.getIdsTiposHabitacionParaEliminar().isEmpty()) {
                for (Integer tipoId : hotel.getIdsTiposHabitacionParaEliminar()) {
                    tipoHabitacionService.eliminarPorId(tipoId);
                }
            }

            // Procesar imágenes nuevas
            if (images != null) {
                for (MultipartFile image : images) {
                    if (!image.isEmpty()) {
                        try {
                            Imagen img = new Imagen();
                            img.setImagen(image.getBytes());
                            img.setIdHotel(hotelId);
                            iImagenService.crearOEditar(img);
                        } catch (Exception e) {
                            throw new RuntimeException("Error al procesar la imagen", e);
                        }
                    }
                }
            }

            // Eliminar imágenes marcadas
            if (hotel.getIdsImagenesParaEliminar() != null && !hotel.getIdsImagenesParaEliminar().isEmpty()) {
                for (Integer imgId : hotel.getIdsImagenesParaEliminar()) {
                    iImagenService.eliminarPorId(imgId);
                }
            }

            return "redirect:/hotel";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el hotel: " + e.getMessage());
            model.addAttribute("hotelForm", hotel);
            List<Zona> zonas = zonaService.obtenerTodos();
            model.addAttribute("zonas", zonas);
            return Objects.equals(authCheck, "authCreate") ? "hotel/create" : "hotel/edit";
        }
    

    }


    // Editar un hotel
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Hotel hotel = hotelService.buscarPorId(id);
        List<Zona> zonas = zonaService.obtenerTodos();
        
        model.addAttribute("hotelForm", hotel);
        model.addAttribute("zonas", zonas);
        return "hotel/edit_reference";
    }



}