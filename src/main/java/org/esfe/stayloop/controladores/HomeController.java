package org.esfe.stayloop.controladores;

import jakarta.servlet.http.HttpServletRequest;
import org.esfe.stayloop.modelos.Usuario;
import org.esfe.stayloop.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    IUsuarioService usuarioService;

    @GetMapping
    public String index(){




        return "home/index";
    }

    @GetMapping("/login" )
    public String mostrarLogin() {
        return "home/formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String mostrarRegistrarse(Usuario usuario, Model model){
        model.addAttribute("rol", 3);
        return "home/formRegister";
    }
}
