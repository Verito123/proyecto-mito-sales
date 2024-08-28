package com.mitocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.dto.CategoryDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Category;
import com.mitocode.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

//JUNIt puedo hacer comprobaciones de verdad o falsedad
/*Este resultado es igual a otro, es mayor que este otro o es menor a este otro
 * Mockito: voy a poder simular resultados, No es necesario trabajar con base de datos
 * */

//Para poder ejecutar los test con Spring Security

//@ActiveProfiles("test")
//@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CategoryController.class)

public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService service;

    @MockBean(name = "categoryMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Category CATEGORY_1 = new Category(1, "TV", "Television", true);
    Category CATEGORY_2 = new Category(2, "PSP", "Play station Portable", true);
    Category CATEGORY_3 = new Category(3, "BOOKS", "Some books", true);

    CategoryDTO CATEGORYDTO_1 = new CategoryDTO(1, "TV", "Television", true);
    CategoryDTO CATEGORYDTO_2 = new CategoryDTO(2, "PSP", "Play station Portable", true);
    CategoryDTO CATEGORYDTO_3 = new CategoryDTO(3, "BOOKS", "Some books", true);
    CategoryDTO CATEGORYDTO_99 = new CategoryDTO(99, "ERROR", "Some ERROR", false);
    @Test
    void readAllTest() throws Exception {
        List<Category> categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3);
        Mockito.when(service.readAll()).thenReturn(categories);

        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);


        mockMvc.perform(MockMvcRequestBuilders      //Cuando escribas la mediatype escribelo completo y no selecciones ninguna de las opciones que marca para que pueda funcionar
                        .get("/categories")
                        .content(MediaType.APPLICATION_JSON_VALUE))//esto es lo que voy a comprobar
                .andExpect(status().isOk())
                //se comprueba que por lo menos categories tenga 3 elementos
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].nameofCategory", is("PSP")));
    }

    //read by id
    //quiero verificar la peticion de category /id
    @Test
    void readByIdTest() throws Exception {
        final int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(CATEGORY_1);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/categories/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameofCategory", is("TV")));
    }

    //METODO SAVE, siempre debe ser de tipo publico
    @Test
    void createTest() throws Exception {
        Mockito.when(service.save(any())).thenReturn(CATEGORY_3);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameofCategory", is("BOOKS")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    void updateTest() throws Exception {
        int ID = 2;

        Mockito.when(service.update(any(), any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameofCategory", is("PSP")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }
    @Test
    void updateErrorTest() throws Exception {
        int ID = 99;

        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID: "+ ID));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_99));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }
    @Test
    public void deleteTest() throws Exception{
        final int ID_CATEGORY =1;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/"+ ID_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); //despues de eliminar que ya no exista el recurso
    }
    //es lo mismo que el metodo de arriba solo que aqui empieza por la excepción y luego lanza el método
    //Mockito lo usa para las simulaciones de instancias bean, mock bean, y JUnit se esta empleando para los @Test, para poder hacer uso de cada prueba
    @Test
    public void deleteErrorTest() throws Exception{
        final int ID_CATEGORY =999;

        Mockito.doThrow(new ModelNotFoundException("ID" +ID_CATEGORY +"NOT FOUND")).when(service).delete(ID_CATEGORY);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/"+ ID_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }
}