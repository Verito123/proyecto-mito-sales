package com.mitocode.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//Clase S1, seguridad 1
//Esta clase me va a servir para 2 objetivos, para poder crear el token con su contenido interno llamado payload
//y para tener metodos utilitarios o validaciones para saber información a partir de ese token

//esta clase la quiero poder estereotipar a si que lleva un @component
@Component
public class JwtTokenUtil implements Serializable {
  
	
	//cada token JWT tiene un tiempo de vigencia, un tiempo en el cual
    /*eso ha expirado y ya no va a ser valido

    * MILISEGUNDOS*/
    public final long JWT_TOKEN_VALIDITY =5 * 60 * 60 *1000; //5HRS EXPRESADAS EN MILISEGUNDOS
    @Value("${jwt.secret}") 								//EL= Expression Language
    private String secret;

    //Payload //valores internos del token claims
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()));
        claims.put("test", "value-test"); //es como un hola mundo
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //se crea un metodo que genera el token final, genera el String que necesito
    
    private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)                                          //que viene a ser el payload con su parametro
                .setSubject(username)                                      //para decir quien genero ese token
                .setIssuedAt(new Date(System.currentTimeMillis()))        //setIssuedAt para saber la fecha en que se genero ese token en milisegundos
                
                
                .setExpiration(new Date(System.currentTimeMillis() +JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey()) 									//para firmar el token, pide una llave como genero esa llave se va a generar con 
                															//un metodo abajo.
                .compact();  												// devuelve un String, y ese String va a ser el token Jwt con un formato parecido
        																	//al de jwt.io
    }
    //el key es propio de Spring security para crear llaves o valores que me
    // permitan autenticar o representar un mecanismo de seguridad
    
    private Key getSigningKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());
    }
    /////////////////////Validaciones////////////////////////////////////
 //   //Obtener todo el contenido del token (claims) //
    
    
    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        
      //se va a decodificar el token, se hace
      // build y con eso se empieza a decodificar el contenido del token y manda el token y se saca el body
      // el body viene a ser el payload, el cuerpo donde esta viajando la información
    }
    																					
    
    
    														//Este metodo va a recibir el token y va a recibir una funcion donde le mandemos a Claims resolver
    														//y va a retornar el claim que necesito
    														// interffaz funcional T porque no se lo que vou a retornar
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token); // se decodifica el token y se ha sacado el body, se almaceno en claims
        return claimsResolver.apply(claims);
        
        
       //Claims es un function: tiene un metodo abstracto que es el
        // apply y es un metodo abstracto de tipo claim y ese es el tipo
        // de retorno
    }
    
    //Para saber el nombre de usuario, de quien genero ese token
    
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject); //mando el token y una función donde pueda mandarle la
        													//información del usuario que ha generado, quiero solicitar el Subject de ese token
    }
    														//para sacar la fecha de expiraciòn del token
    //
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    																//para saber si el token esta expirado o todavia no
    
    private boolean isTokenExpired(String token){
        final Date expiration =getExpirationDateFromToken(token); //saco la fecha de expiracion del token
        return expiration.before(new Date());                  //si la fecha de expiracion esta antes de la fecha actual,segun eso
        														//se retorna el true o false, y con eso puedo saber si el token ha expirado o no
    }
    															//para verificar  si el token es valido o no
    
    public boolean validateToken(String token, UserDetails userDetails){
        final String username =getUsernameFromToken(token);
        return (username.equalsIgnoreCase(userDetails.getUsername())&& !isTokenExpired(token));

        	//  para verificar que el token le pertenece al usuario que esta en la sesion, por eso userDetails va a venir
        	//  de la sesión y aparte verificar si no esta expirado de la logica anterior
    }

}
