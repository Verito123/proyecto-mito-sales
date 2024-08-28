package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //para que ignore los campos nulos
public class UserDTO {
    private Integer idUser;

    @JsonIncludeProperties(value ={"nameRole"}) //los campos queva a aceptar en el json
    @NotNull
    private RoleDTO role;

    @NotNull
    @NotEmpty
    @Size(min =3, max= 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotEmpty
    @Size(min =8, max= 60)
    private String password;

    @NotNull
    private boolean enabled;
}
