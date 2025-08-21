package org.esfe.stayloop.controladores;

import jakarta.validation.Valid;
import org.esfe.stayloop.modelos.Rol;
import org.esfe.stayloop.modelos.Usuario;
import org.esfe.stayloop.servicios.interfaces.IRolService;
import org.esfe.stayloop.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private PasswordEncoder passwordEncoder;

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
    public String save( @RequestParam Integer idRol, @Valid @ModelAttribute("usuario")Usuario usuario, BindingResult bindingResult,  Model model ){
        if(bindingResult.hasErrors()){
            model.addAttribute(usuario);
            model.addAttribute("roles", rolService.obtenerTodos());
            return "admin/create";
        }
        if(usuario.getPassword() != null) {
            String password = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(password);
        }
        else {
            Usuario user = usuarioService.buscarPorId(usuario.getId());
            usuario.setPassword(user.getPassword());
        }

        usuario.setIdRol(idRol);
        usuario.setStatus((byte) 1);
        usuarioService.crearOEditar(usuario);
        return "redirect:/usuarioControl";
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
}
