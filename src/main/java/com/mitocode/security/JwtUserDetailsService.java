package com.mitocode.security;

import com.mitocode.repo.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Clase S2
@Service
@RequiredArgsConstructor

//UserDetailsService es una interfaz propia de Spring Security
// me va a pedir implementar su unico metodo abstracto que tiene
//me va a servir para saber como voy a obtener la información de los usuarios y roles

public class JwtUserDetailsService implements UserDetailsService {
    																//inyecciòn de una interfaz llamada IUserRepo
    private  final IUserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.mitocode.model.User user =repo.findOneByUsername(username);

        if(user ==null){
            throw new UsernameNotFoundException("user not found");
        }
        //user tiene una relación ManyToOne con rol puedo
        //saber el rol que le pertenece al usuario
        
        List<GrantedAuthority> roles = new ArrayList<>();
        String role = user.getRole().getName();
        roles.add(new SimpleGrantedAuthority(role)); //SimpleGrantedAuthority se implementa de Grantes Authorities
        
        														//por eso tiene compatibilidad
        		//User paquete SpringSecutity												//necesito retornar una instancia compatible con esta interfaz
        return new User(user.getUsername(), user.getPassword(), roles);

    }
}
