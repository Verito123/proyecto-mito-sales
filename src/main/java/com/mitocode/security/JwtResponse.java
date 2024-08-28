package com.mitocode.security;

//Clase S4
//solo quiero mostrar el token resultado final, solo quiero mostrar como si fuera
// un dto
//un record porque tengo un constructor con todos los parametros las variables
//Son finales, No hay sets
//@Getter
//@AllArgsConstructor

public record JwtResponse(String jwtToken) {
}
