package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//Es una clase de apoyo no es para base de datos
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//para que ignore los campos nulos
public class ClientDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idClient;

    @NotNull
    @NotEmpty
    @Size(min =3, max =100)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min =3, max =100)
    private String lastName;

    @NotNull
    @NotEmpty
    @Size(min =3, max =100)
    private String country;

    @NotNull
    @NotEmpty
    @Size(min =10, max =100)
    private String cardId;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min =3, max = 100)
    private String address;

}
