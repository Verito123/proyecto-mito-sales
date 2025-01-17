package com.mitocode.controller;

import com.mitocode.dto.RoleDTO;
import com.mitocode.model.Role;
import com.mitocode.service.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor  //Genera el constructor por mi

public class RoleController {

    private final IRoleService service; //=new RoleService();
    @Qualifier("defaultMapper")
    private  final ModelMapper mapper;

    //url: http://localhost:8080/categories

    @GetMapping
    public ResponseEntity<List<RoleDTO>> readAll() throws Exception {
      //  ModelMapper mapper =new ModelMapper();
        List<RoleDTO> list = service.readAll().stream().map(this ::convertToDto)
        .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> readById(@PathVariable("id") Integer id) throws Exception{
        RoleDTO dto =convertToDto(service.readById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto) throws Exception {
        Role obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO dto, @PathVariable("id") Integer id) throws Exception {
        Role obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private RoleDTO convertToDto(Role obj){
        return mapper.map(obj, RoleDTO.class);
    }
    private Role convertToEntity(RoleDTO dto){
        return mapper.map(dto, Role.class);
    }
}

