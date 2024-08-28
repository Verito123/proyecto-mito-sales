package com.mitocode.controller;

import com.mitocode.dto.ClientDTO;
import com.mitocode.model.Client;
import com.mitocode.service.IClientService;
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
@RequestMapping("/clients")
@RequiredArgsConstructor  //Genera el constructor por mi

public class ClientController {

    private final IClientService service; //=new ClientService();
    @Qualifier("defaultMapper")
    private  final ModelMapper mapper;

    //url: http://localhost:8080/categories

    @GetMapping
    public ResponseEntity<List<ClientDTO>> readAll() throws Exception {
      //  ModelMapper mapper =new ModelMapper();
        List<ClientDTO> list = service.readAll().stream().map(this ::convertToDto)
        .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> readById(@PathVariable("id") Integer id) throws Exception{
        ClientDTO dto =convertToDto(service.readById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO dto) throws Exception {
        Client obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@Valid @RequestBody ClientDTO dto, @PathVariable("id") Integer id) throws Exception {
        Client obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private ClientDTO convertToDto(Client obj){
        return mapper.map(obj, ClientDTO.class);
    }
    private Client convertToEntity(ClientDTO dto){
        return mapper.map(dto, Client.class);
    }
}