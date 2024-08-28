package com.mitocode.controller;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.dto.SaleDTO;
import com.mitocode.model.Sale;
import com.mitocode.service.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor  //Genera el constructor por mi

public class SaleController {

    private final ISaleService service; //=new SaleService();
    @Qualifier("defaultMapper")
    private  final ModelMapper mapper;

    //url: http://localhost:8080/categories

    @GetMapping
    public ResponseEntity<List<SaleDTO>> readAll() throws Exception {
      //  ModelMapper mapper =new ModelMapper();
        List<SaleDTO> list = service.readAll().stream().map(this ::convertToDto)
        .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> readById(@PathVariable("id") Integer id) throws Exception{
        SaleDTO dto =convertToDto(service.readById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody SaleDTO dto) throws Exception {
        Sale obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@Valid @RequestBody SaleDTO dto, @PathVariable("id") Integer id) throws Exception {
        Sale obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    ///////////////Queries////////////////////////////////////////////////
    @GetMapping("/resume")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume(){
        return new ResponseEntity<>(service.callProcedure(), HttpStatus.OK);
    }
    @GetMapping("/resume2")
    public ResponseEntity<List<IProcedureDTO>> getSaleResume2(){
        return new ResponseEntity<>(service.callProcedure2(), HttpStatus.OK);
    }
    @GetMapping("/resume3")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume3(){
        return new ResponseEntity<>(service.callProcedure3(), HttpStatus.OK);
    }
    //manda a llamar a un`procedimiento que convierte en true a las ventas
    @GetMapping("/resume4")
    public ResponseEntity<List<Void>> getSaleResume4() {
        service.callProcedure4();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/mostexpensive")
    public ResponseEntity<SaleDTO> getMostExpensive() {
        SaleDTO dto =convertToDto(service.getSaleMostExpensive());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/bestseller")
    public ResponseEntity<String> getBestSeller() {
      String user =service.getBestSellerPerson();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/sellercount")
    public ResponseEntity<Map<String, Long>> getSellerCount() {
     Map<String, Long>byUser =service.getSaleCountBySeller();
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }
    @GetMapping("/bestproduct")
    public ResponseEntity<Map<String, Double>> getBestProduct() {
        Map<String, Double> byUser =service.getMostSellerProduct();
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }



/////////////////////////////////////////////////////////////////////////////////
    private SaleDTO convertToDto(Sale obj){
        return mapper.map(obj, SaleDTO.class);
    }
    private Sale convertToEntity(SaleDTO dto){
        return mapper.map(dto, Sale.class);
    }
}

