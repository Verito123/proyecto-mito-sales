package com.mitocode.repo;

import com.mitocode.model.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryRepo extends IGenericRepo <Category, Integer>{

    //DerivedQueries consiste en crear querys a traves de metodos
    //SELECT* FROM Category c WHERE c.name c.name= '';
    // SELECT * FROM Category c WHERE c.name LIKE '%abc%';

    //Devolvera una lista de categorias
    List<Category> findByName(String name); //este es un query, el valor a buscar se escribe en el metodo
    List<Category> findByNameLike(String name);

    //SQL: SELECT *FROM Category c WHERE c.name =? AND enabled =?
    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> findByNameOrEnabled(String name, boolean enabled);
    List<Category> findByEnabled(boolean enabled); //si me interesa buscar por un solo campo
    List<Category> findByEnabledTrue(); // si solo quiero buscar por los que son true
    List<Category> findByEnabledFalse(); //si solo quiero buscar por los que son false
    Category findOneByName(String name); //findOne me devuelve un solo resultado si solo quiero encontrar una sola coincidencia o resultado
                                         //findBy me devuelve una lista de coincidencias
    List<Category>findTop3ByName(String name); //Lista de los primeros 3 elementos
    List<Category>findTop5ByName(String name);  //Lista de los primeros 5 elementos
    List<Category> findByNameIs(String name); // para tener la clausula is
    List<Category> findByNameIsNot(String name); // si quisiera negar el is
    List<Category> findByNameIsNull(); // si quisiera saber los que son nulos
    List<Category> findByNameIsNotNull(); // si quisiera saber aquellos que no son nulos
    List<Category> findByNameEqualsIgnoreCase(String name); //si quisiera saber aquellos nombres sin importar mayusculas o minusculas
    List<Category> findByIdCategoryLessThan(Integer id); //si quisiera saber con un valor numerico si es menor o mayor a un numero
    List<Category> findByIdCategoryLessThanEqual(Integer id); // menor que igual
    List<Category> findByIdCategoryGreaterThan(Integer id); //menor que
    List<Category> findByIdCategoryGreaterThanEqual(Integer id); //mayor que igual
    List<Category> findByIdCategoryBetween(Integer id1, Integer id2);// esta entre uno y otro
    List<Category> findByNameOrderByDescription(String name);
    List<Category> findByNameOrderByDescriptionAsc(String name);
    List<Category> findByNameOrderByDescriptionDesc(String name);

    //JPQL: Java Persistence Query Language
    //son mas simples y llevan desde el from ya no usa select *
    @Query("FROM Category c WHERE c.name = :name AND c.description LIKE %:description%")
    List<Category>getNameAndDescription2(@Param("name")String name, @Param("description")String description);

    //@Query("SELECT new com.mitocode.model.Category
    //@Query("SELECT new Category
    @Query("SELECT new com.mitocode.model.Category (c.idCategory, c.name, c.description, c.enabled) FROM Category c WHERE c.name = :name AND c.description LIKE %:description%")
    List<Category>getNameAndDescription1(@Param("name")String name, @Param("description")String description);

    /*cuando la consulta es de tipo nativa es sensible a mayusculas por lo que la entidad
    * Category<-debe ir en minusculas para mantener la tabla que esta en base de datos  */
    @Query(value ="SELECT c.id_category, c.name, c.description, c.enabled FROM category c WHERE c.name = :name", nativeQuery = true) //nativeQuery si es false pensara que es un JPQL
    List<Category>getNameSql(@Param("name")String name);

/*@Query(value ="SELECT * FROM category c WHERE c.name = :name", nativeQuery = true) //nativeQuery si es false pensara que es un JPQL
    List<Category>getNameSql(@Param("name")String name);*/

    /*@Query(value ="SELECT c.id_category, c.name, c.enabled FROM category c WHERE c.name = :name", nativeQuery = true) //nativeQuery si es false pensara que es un JPQL
    List<Object[]>getNameSql(@Param("name")String name);*/
    @Modifying //va en insersion, modificacion y eliminación
    @Query(value = "UPDATE category SET name =:name", nativeQuery = true)
    Integer updateNames(@Param("name")String name);
}


//%XYZ% ->findByNameContains porcetaje total en ambos lados
//%XYZ ->findByNameStarsWith expresar solamente a la izquierda
//XYZ% ->findByNameEndsWith expresar solamente a la derecha

