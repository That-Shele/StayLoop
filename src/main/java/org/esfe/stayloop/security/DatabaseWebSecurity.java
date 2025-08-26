package org.esfe.stayloop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleAuthenticationSuccessHandler();
    }

    @Bean
    public UserDetailsManager customUsers(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select email, password, status from usuarios where email = ?");
        users.setAuthoritiesByUsernameQuery("select u.email, r.nombre from usuarios u " +
                "inner join roles r on r.id = u.id_rol " +
                "where email = ?");
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize
                // aperturar el acceso a los recursos estáticos
                //.requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
                // las vistas públicas no requieren autenticación
                .requestMatchers("/register", "/", "/login","/usuarioControl/save", "/error", "/hotel/portada/**").permitAll()



                // Asignar permisos a URLs por ROLES
                .requestMatchers("/usuarioControl/imagen/**").authenticated()
                .requestMatchers("/usuarioControl/perfil").authenticated()
                .requestMatchers("/hotel/imagen/**").authenticated()
                .requestMatchers("/usuarioControl/**").hasAnyAuthority("admin")

//                .requestMatchers("/hotel/**").hasAnyAuthority("admin", "hotel")
//                .requestMatchers("/admin/**").hasAnyAuthority("admin")
//                .requestMatchers("/alumnos/**").hasAnyAuthority("admin", "docente")

                // todas las demás vistas requieren autenticación
                .anyRequest().authenticated());
        http.formLogin(form -> form.loginPage("/login").permitAll()
                .successHandler(myAuthenticationSuccessHandler()));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
