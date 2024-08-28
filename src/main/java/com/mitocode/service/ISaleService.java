package com.mitocode.service;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;

import java.util.List;
import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer> {
	List<ProcedureDTO> callProcedure();

	List<IProcedureDTO> callProcedure2();

	List<ProcedureDTO> callProcedure3();

	void callProcedure4();

	Sale getSaleMostExpensive(); // obtener la venta mayor

	String getBestSellerPerson(); // obtener el nombre del mejor vendedor

	Map<String, Long> getSaleCountBySeller(); // contar cantidades de ventas por vendedor

	Map<String, Double> getMostSellerProduct(); // producto mas vendido

}
