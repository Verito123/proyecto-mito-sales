package com.mitocode.config;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.dto.ProductDTO;
import com.mitocode.model.Category;
import com.mitocode.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //sirve como clase de configuracion
public class MapperConfig {
    @Bean("categoryMapper")
    public ModelMapper modelMapper(){

         ModelMapper mapper =new ModelMapper();

         //Lectura
        TypeMap<Category, CategoryDTO>typeMap1= mapper.createTypeMap(Category.class, CategoryDTO.class);
        typeMap1.addMapping(Category::getName, (dest, v)->dest.setNameofCategory((String) v));

        //Escritura
        TypeMap<CategoryDTO, Category>typeMap2= mapper.createTypeMap(CategoryDTO.class, Category.class);
        typeMap2.addMapping(CategoryDTO::getNameofCategory, (dest, v)-> dest.setName((String) v));

         return mapper;


    }
    @Bean("productMapper")
    public ModelMapper productMapper(){
        ModelMapper mapper =new ModelMapper();
        TypeMap<Product, ProductDTO>typeMap1= mapper.createTypeMap(Product.class, ProductDTO.class);
        typeMap1.addMapping(e->e.getCategory().getIdCategory(), (dest, v)->dest.setIdCategory((Integer) v));

        TypeMap<ProductDTO, Product>typeMap2= mapper.createTypeMap(ProductDTO.class, Product.class);
        typeMap2.addMapping(ProductDTO::getIdCategory, (dest, v)->dest.getCategory().setIdCategory((Integer) v));
        return mapper;

}
    @Bean("defaultMapper")
    public ModelMapper defaultMapper(){
        return  new ModelMapper();
    }
}
