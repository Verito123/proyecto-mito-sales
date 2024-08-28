package com.mitocode.repo;

import com.mitocode.dto.IProcedureDTO;
import com.mitocode.dto.ProcedureDTO;
import com.mitocode.model.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface ISaleRepo extends IGenericRepo<Sale, Integer> {
	
	@Query(value = "select * from fn_sales()", nativeQuery = true)
	List<Object[]> callProcedure(); // mando a llamar al procedimiento que esta en base de datos

	
	@Query(value = "select * from fn_sales()", nativeQuery = true)
	List<IProcedureDTO> callProcedure2();

	
	
	// "Sale.fn_sales: es un nombre aleatorio en que quieras colocarle"
	// pero el estandar es colocar el nombre de la entidad seguido del nombre de la
	// funcion o entidad que se va a trabajar
	@Query(name = "Sale.fn_sales", nativeQuery = true)
	List<ProcedureDTO> callProcedure3();

	@Procedure(procedureName = "pr_sales")
	void callProcedure4();
}
