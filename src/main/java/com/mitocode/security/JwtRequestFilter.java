package com.mitocode.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

//Clase S5 (clase importante porque ese va a ser un filtro, que va a interceptar todas las
// peticiones http, es decir ya me loguee, una vez que me logueo quiero hacer otras futuras
// peticiones, pero cuando le doy send tengo que evaluar si esta
// viajando un token
// si ese token es valido, no expirado, tengo que hacer validaciones antes de dar la respuesta final)

//Aqui es donde voy a evaluar todo el contenido http para poder hacer uso del token

//Para poder ejecutar los test con Spring Security
//@Profile(value= {"dev", "uat"})
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    //como inyecciones voy a traer JwtUserDetailsService para saber la informacion de donde están los usuarios
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil; // para poder crear el token validar con los metodos que se crearon
    //saber el nombre del token etc.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization"); //esta linea es Bearer y todo el token
        String username = null;
        String jwtToken = null;

        if (tokenHeader != null) {
            if (tokenHeader.startsWith("Bearer ") || tokenHeader.startsWith("bearer ")) {
                //          jwtToken =tokenHeader.split("")[1];
                //split lo que hace es separar el token en 2 epacios ("bearer || token")
                final int TOKEN_POSITION = 7; //         [012345 6] token
                //la posiciçon del token es porque cuenta Bearer
                jwtToken = tokenHeader.substring(TOKEN_POSITION);

                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken); //sacar el nombre de usuario
                } catch(Exception e) {
                    request.setAttribute("exception", e.getMessage()); //para capturar el mensaje adecuado de lo que paso

                }
            }
        }
////jwtUserDetailsService es la instancia que me permitia saber la información de ese usuario que estaba cargado
// a traves de loadUserByUsername
        if(username !=null){
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)){  //pide 3 parametros en su constructor 1.-la informacion de userDetails
                //credentials: que viene a ser la clave del usuario se deja con null para no exponer la clave de Spring
                UsernamePasswordAuthenticationToken userPassAuthToken =new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}

