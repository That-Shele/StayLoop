package org.esfe.stayloop.controladores;

import jakarta.validation.Valid;
import org.esfe.stayloop.modelos.Reserva;
import org.esfe.stayloop.modelos.Rol;
import org.esfe.stayloop.modelos.Usuario;
import org.esfe.stayloop.servicios.interfaces.IHotelService;
import org.esfe.stayloop.servicios.interfaces.IReservaService;
import org.esfe.stayloop.servicios.interfaces.IRolService;
import org.esfe.stayloop.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.esfe.stayloop.servicios.utilerias.PdfGeneratorService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuarioControl")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @Autowired
    private IReservaService reservaService;

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size,
                        @RequestParam("idRol") Optional<Integer> idRol,
                        @RequestParam("nombre") Optional<String> nombre,
                        @RequestParam("email") Optional<String> email)
    {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Sort ordenamiento = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(currentPage, pageSize, ordenamiento);

        String filtroNombre = nombre.orElse("");
        String filtroEmail = email.orElse("");
        Integer filtroRol = idRol.orElse(null);

        Page<Usuario> usuarios = usuarioService.buscarPaginados(filtroNombre, filtroEmail, filtroRol, pageable);
        List<Rol> roles = rolService.obtenerTodos();



        for(Usuario usuario : usuarios){
            usuario.setRol(rolService.buscarPorId(usuario.getIdRol()));
        }

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);


        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("rol", idRol);
        model.addAttribute("email", filtroEmail);
        model.addAttribute("nombre", filtroNombre);

        return "admin/usersList";
    }

    @GetMapping("/create")
    public String create(Model model, Usuario usuario){
        model.addAttribute("roles", rolService.obtenerTodos());
        return "admin/create";
    }

    @PostMapping("/save")
    public String save( @RequestParam("idRol") Integer idRol,
                        @RequestParam("img") MultipartFile imgUsuario,
                        @RequestParam("authCheck") String authCheck,
                        @Valid @ModelAttribute("usuario") Usuario usuario,
                        BindingResult bindingResult, Model model
                        ){
        if(bindingResult.hasErrors()){
            model.addAttribute(usuario);
            model.addAttribute("roles", rolService.obtenerTodos());
            if(Objects.equals(authCheck, "authCreate")) {
                return "admin/create";
            }
          else if (Objects.equals(authCheck, "authEdit")) {
            return "admin/edit";
         }
        else {
                return "home/formRegister";
            }
        }
        if (imgUsuario != null && !imgUsuario.isEmpty()) {
            try{
                usuario.setImgUsuario(imgUsuario.getBytes());

            }
            catch (Exception e){
                model.addAttribute(usuario);
                model.addAttribute("roles", rolService.obtenerTodos());
                if(Objects.equals(authCheck, "authCreate")) {
                    return "admin/create";
                } else if (Objects.equals(authCheck, "authEdit")) {
                    return "admin/edit";
                } else {
                    return "home/formRegister";
                }
            }
        }
        else {
            Usuario user = usuarioService.buscarPorId(usuario.getId());
            usuario.setImgUsuario(user.getImgUsuario());
        }



        if(usuario.getPassword() != null &&Objects.equals(authCheck, "authEdit") ) {
            Usuario user = usuarioService.buscarPorId(usuario.getId());
            usuario.setPassword(user.getPassword());
        }
        else {
            String password = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(password);
        }



        usuario.setIdRol(idRol);
        usuario.setStatus((byte) 1);
        usuarioService.crearOEditar(usuario);
        if(Objects.equals(authCheck, "anon")) {
            return "redirect:/";
        }
        else {

            return "redirect:/usuarioControl";
        }
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioService.buscarPorId(id);
        Rol rol = rolService.buscarPorId(usuario.getIdRol());
        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);
        return "admin/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("roles", rolService.obtenerTodos());
        model.addAttribute("usuario", usuario);
        return "admin/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioService.buscarPorId(id);
        Rol rol = rolService.buscarPorId(usuario.getIdRol());
        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);
        return "admin/delete";
    }

    @PostMapping("/delete")
    public String remove(Usuario usuario){
        usuarioService.eliminarPorId(usuario.getId());
        return "redirect:/usuarioControl";
    }

    @GetMapping("/imagen/{email}")
    @ResponseBody
    public byte[] mostrarImagen(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(Usuario::getImgUsuario)
                .orElse(null);
    }

    @GetMapping("/reportegeneral/{visualizacion}")
    public ResponseEntity<byte[]> ReporteGeneral(@PathVariable("visualizacion") String visualizacion) {

        try {
            List<Usuario> usuarios = usuarioService.obtenerTodos();

            // Genera el PDF. Si hay un error aquí, la excepción será capturada.
            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("reportes/rpUsuarios", "usuarios", usuarios);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // inline= vista previa, attachment=descarga el archivo
            headers.add("Content-Disposition", visualizacion+"; filename=reporte_general.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/perfil")
    public String perfil(Model model,
                         @RequestParam(value = "page", required = false) Optional<Integer> page,
                         @RequestParam(value = "size", required = false) Optional<Integer> size,
                         @RequestParam(value = "idUsuario", required = false) Optional<Integer> idUsuario,
                         @RequestParam(value = "idHotel", required = false) Optional<Integer> idHotel,
                         @RequestParam(value = "totalMin", required = false) Optional<BigDecimal> totalMin){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioService.buscarPorEmail(email).get();
        idUsuario = usuario.getId().describeConstable();

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

        model.addAttribute("reservas", reservas);
        model.addAttribute("usuario", usuario);
        model.addAttribute("hoteles", hotelService.obtenerTodos());
        model.addAttribute("rol", rolService.buscarPorId(usuario.getIdRol()));

        return "usuario/perfil";
    }

}
