package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Es una clase de apoyo no es para base de datos
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//para que ignore los campos nulos
public class CategoryDTO {
    private Integer idCategory;

    @NotNull
    @NotEmpty
    @Size(min =3, max=50, message = "name min 3") //Evalua la cantidad de caracteres en una cadena de texto
    private String nameofCategory;

    @NotNull
    @NotEmpty
    @Size(min =3, max=50)
    private String descriptionCategory;

    @NotNull
    private boolean enabledCategory;

}
/*
    @Max(value =99)
    @Min(value = 1)
    private int age;

    @Email
  //  @Past //para saber si una fecha esta antes de otra fecha
    @Pattern(regexp = "[0-9]+") //para mandar una expresion regular
    private String email;*/



