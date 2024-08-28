package com.mitocode.dto;

import com.mitocode.model.Category;

//record es una clase que me permite generar un objeto inmutable
//su popularidad se puede ver en el caso de generar dtos, datos de salida
public record CategoryRecord(
        Category category
) {
}
