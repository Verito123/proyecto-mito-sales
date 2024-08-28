package com.mitocode.service;
import com.mitocode.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ICategoryService extends ICRUD<Category, Integer>{

    List<Category> findByName(String name); //este es un query, el valor a buscar se escribe en el metodo
    //en findByName se le puede agregar otro nombre, opcional
    //List<Category>getCategoriesDisabled;
    List<Category> findByNameLike(String name);

    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category>getNameAndDescription1(String name, String description);
    List<Category> getNameSQL(@Param("name")String name);
    //List<Object[]> getNameSQL(@Param("name")String name);

    /*Listar elementos, no con select *from, si no que debe tener la capacidad de devolver los 5 primeros
    * los 3 primeros, y pedir n siguientes como si fueran bloques de paginas para no estar saturando a la base de datos y
    * TAMPOCO AL CLIENTE
    * Esto va a retornar una pagina de algo en este caso una pagina de una categoría*/
    Page<Category>findPage(Pageable pageable);
    //ordena en orden ascendente descendente
    List<Category>findAllOrder(String param);
}
