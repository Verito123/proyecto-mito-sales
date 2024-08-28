package com.mitocode.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Clase S3


@Data
@AllArgsConstructor
@NoArgsConstructor
								//servira para cuando inicie sesión
public class JwtRequest {
    private String username;
    private String password;
}
