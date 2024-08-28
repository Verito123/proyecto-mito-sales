package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//para que ignore los campos nulos
public class ProductDTO {
    private Integer idProduct;

    @NotNull
    @Min(value = 1)
    private Integer idCategory;

    @NotNull
    @NotEmpty
    private String nameProduct;

    @NotNull
    @NotEmpty
    private String descriptionProduct;

    @Min(value = 1)
    private double priceProduct;

    @NotNull
    private boolean enabledProduct;
}
