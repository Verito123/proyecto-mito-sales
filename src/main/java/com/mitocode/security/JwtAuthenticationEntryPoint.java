package com.mitocode.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.exception.CustomErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

//Clase S6
//Sirve para personalizar esas situaciones de errores que puedan pasar al momento de crear el token
//indicca el mensaje del error que tiene al momento que se lanza la excepcion
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exceptionMsg = (String) request.getAttribute("exception");

        if (exceptionMsg == null) { //si llega un nulo
            exceptionMsg = "Token not found";
        }
        CustomErrorResponse errorResponse = new CustomErrorResponse(LocalDateTime.now(), exceptionMsg, request.getRequestURI());
        
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        response.getWriter().write(convertObjectToJson(errorResponse));
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                 //en vez de usar librerias GSON
       
            }

        private String convertObjectToJson(Object object) throws JsonProcessingException{
            if (object ==null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(object); // ES EL JSON QUE SE ESTA GENERANDO
        }
    }

