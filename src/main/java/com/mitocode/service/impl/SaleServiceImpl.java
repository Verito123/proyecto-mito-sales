package com.mitocode.service.impl;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;
import com.mitocode.model.SaleDetail;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.ISaleRepo;
import com.mitocode.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import java.util.*;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    private final ISaleRepo repo;

    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    /*
    lista de arreglo de objetos// lo de adentro es un object
    [4, "13/09/2023"] 0
    [5, "13/09/2023"] 1
    * */

    @Override
    public List<ProcedureDTO> callProcedure() {
        List<ProcedureDTO>list = new ArrayList<>();
         repo.callProcedure().forEach(e->{  //for each: para cada elemento de esa lista
             ProcedureDTO dto = new ProcedureDTO();
             dto.setQuantityfn((Integer) e[0]); //elemento en la posicion 0
             dto.setDatetimefn((String) e[1]);
             list.add(dto);
         });
         return list;
    }

    @Override
    public List<IProcedureDTO> callProcedure2() {
        return repo.callProcedure2();
    }

    @Override
    public List<ProcedureDTO> callProcedure3() {
        return repo.callProcedure3();
    }

    @Override
    public void callProcedure4() {
        repo.callProcedure4();
    }

    @Override
    public Sale getSaleMostExpensive() {
        return repo.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getTotal))
                .orElse(new Sale());
    }

    //utilizando api stream uso de collect
    @Override
    public String getBestSellerPerson() {
        Map<String, Double> byUser = repo.findAll()
                .stream()                              //summingDouble: suma todos los campos de tipo total
                .collect(groupingBy(s -> s.getUser().getUsername(), summingDouble(Sale::getTotal)));
     //   System.out.println(byUser);
        return Collections.max(byUser.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey(); //el key es el que tiene el stream

    }

    @Override
    public Map<String, Long> getSaleCountBySeller() {
        return repo.findAll()
                .stream()
                .collect(groupingBy(s->s.getUser().getUsername(), counting()));
    }

    @Override
    public Map<String, Double> getMostSellerProduct() {
        Stream<Sale> saleStream = repo.findAll().stream();
        Stream<List<SaleDetail>> lsStream = saleStream.map(Sale::getDetails);
        //flatmap: es un operador que sirve para transformar datos pero tambien te permite
        //ver el contenido que tiene un stream
        //flat map es como un win zip o un win rar que descomprime el stream y te muestra directamente el contenido interno
        //flat: plano aplanador

        //


        Stream<SaleDetail> streamDetail = lsStream.flatMap(Collection::stream);
        Map<String, Double>byProduct = streamDetail
                .collect(groupingBy(d->d.getProduct().getName(),summingDouble(SaleDetail::getQuantity)));
        //System.out.println(byProduct);
      //  return byProduct;

        /**/return byProduct.entrySet()
                .stream()  //se agrego la libreria de Comparator y se elimina de ahi
                .sorted(Map.Entry.comparingByValue(/*Comparator.*/reverseOrder()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue)->oldValue, LinkedHashMap::new//-->new linkedHashMap<>()
                ));/**/


    }

}
