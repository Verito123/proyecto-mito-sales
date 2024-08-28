package com.mitocode.controller;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.dto.CategoryRecord;
import com.mitocode.model.Category;
import com.mitocode.model.Product;
import com.mitocode.service.ICategoryService;
import com.mitocode.service.IProductService;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor  //Genera el constructor por mi

public class CategoryController {

    private final ICategoryService service; //=new CategoryService();
    @Qualifier("categoryMapper")
    private  final ModelMapper mapper;

    //url: http://localhost:8080/categories

   // @PreAuthorize("hasAuthority('ADMIN')or hasAuthority('USER')") //dar permisos de administrador tambien acepta logica booleana
    
    @PreAuthorize("@authServiceImpl.hasAccess('readAll')")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> readAll() throws Exception {
      //  ModelMapper mapper =new ModelMapper();
        List<CategoryDTO> list = service.readAll().stream().map(this ::convertToDto)
        .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> readById(@PathVariable("id") Integer id) throws Exception{
        CategoryDTO dto =convertToDto(service.readById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) throws Exception {
        Category obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto, @PathVariable("id") Integer id) throws Exception {
    	 final Logger logger = (Logger) LoggerFactory.getLogger(Category.class);
       
    	 Category obj = service.update(convertToEntity(dto), id);
    	 
    	 logger.info("Updated object: " + dto); //StudentsDto(idStudents=8, nombreStudents=Armando, apellidosStudents=Rojas Saldivar, dniStudents=ISC141581, edadStudents=28)
 	     logger.info("Updated object: " + obj);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    ///////////////Queries///////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/find/name/{name}")
    public ResponseEntity<List<CategoryDTO>>findByName(@PathVariable("name")String name){
        List<CategoryDTO> list =service.findByName(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/find/name/like/{name}")
    public ResponseEntity<List<CategoryDTO>>findByNameLike(@PathVariable("name")String name){
        List<CategoryDTO> list =service.findByNameLike(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/find/name/enabled/{name}/{enabled}")
    public ResponseEntity<List<CategoryDTO>>findByNameAndEnabled(@PathVariable("name")String name, @PathVariable("enabled")boolean enabled ){
        List<CategoryDTO> list =service.findByNameAndEnabled(name, enabled).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get/name/description")
    public ResponseEntity<List<CategoryDTO>>getNameAndDescription(@RequestParam("name")String name, @RequestParam("description")String description) throws Exception{
        List<CategoryDTO> list =service.getNameAndDescription1(name, description).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    /* @GetMapping("/get/name/sql/{name}")
        public ResponseEntity<List<Object[]>>getNameSQL(@PathVariable("name")String name) throws Exception {
                List<Object[]> list = service.getNameSQL(name);//.stream().map(this::convertToDto).toList();
                return new ResponseEntity<>(list, HttpStatus.OK);*/
        @GetMapping("/get/name/sql/{name}")
        public ResponseEntity<List<CategoryDTO>>getNameSQL(@PathVariable("name")String name) throws Exception {
            List<CategoryDTO> list = service.getNameSQL(name).stream().map(this::convertToDto).toList();
            return new ResponseEntity<>(list, HttpStatus.OK);
    }
    ////////////////////Paginación/////////////////////////////////////////////////////////
        @GetMapping("/pagination")
        public ResponseEntity<Page<CategoryDTO>>findPage(Pageable pageable){
            Page<CategoryDTO>pageCategory =service.findPage(pageable).map(this::convertToDto);
            return new ResponseEntity<>(pageCategory, HttpStatus.OK);
    }
    @GetMapping("/pagination2")
    public ResponseEntity<Page<CategoryDTO>>findPage(
            @RequestParam(name ="p", defaultValue = "0")int page,
            @RequestParam(name ="s", defaultValue = "3")int size
    ){
        PageRequest pageRequest =PageRequest.of(page, size);
        Page<CategoryDTO>pageCategory =service.findPage(pageRequest).map(this::convertToDto);
        return new ResponseEntity<>(pageCategory, HttpStatus.OK);
    }
    @GetMapping("/order")
    public ResponseEntity<List<CategoryDTO>>findAllOrder(@RequestParam(name ="param", defaultValue ="ASC")String param)throws Exception{
    List<CategoryDTO>lst =service.findAllOrder(param).stream().map(this::convertToDto).collect(Collectors.toList());
    return new ResponseEntity<>(lst, HttpStatus.OK);
    }
    /*
    *  @GetMapping("/get/name/description")
    public ResponseEntity<List<CategoryDTO>>getNameAndDescription(@RequestParam("name")String name, @RequestParam("description")String description) throws Exception{
        List<CategoryDTO> list =service.getNameAndDescription1(name, description).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    * */
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private CategoryDTO convertToDto(Category obj){
        return mapper.map(obj, CategoryDTO.class);
    }
    private Category convertToEntity(CategoryDTO dto){
        return mapper.map(dto, Category.class);
    }
}

 /*
    @GetMapping
    public String sayHello(){
        return "Hola Coders";
    }
    */

    /*
    public CategoryController(CategoryService service) {
        this.service = service;
    }*/

     /*
    @GetMapping
    public Category saveCategory(){
         Category category =new Category(0, "PC", "TEST",true);
         return  service.saveAndValid(category);
     }*/
