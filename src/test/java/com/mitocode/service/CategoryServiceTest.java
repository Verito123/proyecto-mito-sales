package com.mitocode.service;

import com.mitocode.model.Category;
import com.mitocode.repo.ICategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//voy a probar la parte de la logica del negocio
//ya no necesito la anotación @WebMvcTest(CategoryController.class) // del escenario de pruebas test
@ExtendWith(SpringExtension.class) // de esta manera se esta activando el comportamiento para poder traer algunas instancias o algo que se necesite de lo
//que se ha realizado en el lado de desarrollo, sin esa anotación no podría reconocer a esa clase como tal
public class CategoryServiceTest {
    @MockBean
    private CategoryServiceImpl service; //se realiza con la implementación de una interfaz
    @MockBean
    private ICategoryRepo repo;
    private Category CATEGORY_1;
    private Category CATEGORY_2;
    private Category CATEGORY_3;

    @BeforeEach
    public void init(){

        //azucar sintactico
        MockitoAnnotations.openMocks(this);
        this.service =new CategoryServiceImpl(repo);
         CATEGORY_1 = new Category(1, "TV", "Television", true);
         CATEGORY_2 = new Category(2, "PSP", "Play station Portable", true);
         CATEGORY_3 = new Category(3, "BOOKS", "Some books", true);

        List<Category>categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3);
        Mockito.when(repo.findAll()).thenReturn(categories);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(CATEGORY_1));
        //SE ESTA SIMULANDO EL GUARDADO
        Mockito.when(repo.save(any())).thenReturn(CATEGORY_1);
    }//any es un metodo comodin, para cuando se busca algun comodin
    @Test
    void  readAllTest() throws Exception{
        List<Category>response =service.readAll();
        //hacer comprobaciones de verdad y ver que eso no me cause un nulo
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.size(), 3);
    }
    @Test
    void  readByIdTest() throws Exception{
        final int ID = 1;
        Category response = service.readById(ID);
        //hacer comprobaciones de verdad y ver que eso no me cause un nulo
        assertNotNull(response);

    }
    //SE GUARDA Y SE COMPRUEBA QUE LA RESPUESTA NO SEA NULA PORQUE GUARDO A CATEGORY_1
    @Test
    void save() throws Exception{
        Category response = service.save(CATEGORY_1);
        //hacer comprobaciones de verdad y ver que eso no me cause un nulo
        assertNotNull(response);
    }
    @Test
    void update() throws Exception{
        Category response = service.update(CATEGORY_1, any());
        //hacer comprobaciones de verdad y ver que eso no me cause un nulo
        assertNotNull(response);
    }
    @Test
    void delete() throws Exception{
        final int ID =1;
        repo.deleteById(ID);
        //las veces que se puede ejecutar
        // repo.deleteById(ID);
        // repo.deleteById(ID);
        // repo.deleteById(ID);
        //Estoy verificando que se haya ejecutado esa cantidad de veces el metodo deleteById
        verify(repo, times(1)).deleteById(ID);
    }
}
