package com.mitocode.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Para poder ejecutar los test con Spring Security
//@Profile(value= {"dev", "uat"})

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // este es obligatorio solo cuando quieras validar cierta logica de que accesa a ciertos metodos
//con esto funciona el @PreAuthorize
//esta clase es para saber que rutas voy a proteger cuales no protejo y activar las seguridades que se han venido configurando
//en los pasos previos
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailService;
    private final JwtRequestFilter jwtRequestFilter;

    //el primer bean va a instanciar una clase de tipo AuthenticationManager, es como para decirle a Spring
    // inicializo una configuracion de spring para manejar un proveedor de autenticación de seguridad
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    //para que spring sepa como utilizar las claves codificadas(Esta encriptado en BCript)
    // lo que decimos es que vamos a utilizar como estrategia para codificar las claves a una instancia de BCript
    //passwordEncoded y es una libreria que viene dentro de springboot
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(jwtUserDetailService).passwordEncoder(passwordEncoder());
    }
    @Bean  //SecurityFilterChain: filtro que va a evaluar las rutas de que es lo que proteges y que es lo que liberas
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        //Desde Spring Boot 3.1
        httpSecurity //con este httpSecurity vamos a deshabilitar una estrategia llamada csrf
                .csrf(AbstractHttpConfigurer::disable) //csrf : es un ataque malicioso a los formulariosde una web en
                //peticiones de tipo post// Cross-Site Request Forgery //.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(req ->req
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/rest/**").permitAll()
                        .requestMatchers("/categories/**").permitAll()
                        .anyRequest().authenticated()//cualquier cosa que no este listada en la parte superior
                        //pidele autenticacion
                )
                .httpBasic(Customizer.withDefaults()) //la configuración basica hay que deshabilitarla con Customizer
                .exceptionHandling(e->e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                //.formLogin().disable() //vamos a deshabilitar ese formulario que viene por defecto cuando se agrego la seguridad de spring
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(Customizer.withDefaults());// la gestion de sesiones indicada por defecto
       //se agrega un filtro extra, antes de filter Security change agregamos el filtro que hemos creado
        //jwtRequestFilter, y eso me devuelve un UsernamePasswordAuthenticationFilter.class
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
