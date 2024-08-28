package com.mitocode.model;

import com.mitocode.dto.ProcedureDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
//Spring data acopla la logica del acceso a datos con el modelo
@SqlResultSetMapping(
        name= "Procedure.Procedure.DTO",
        classes =@ConstructorResult(targetClass = ProcedureDTO.class,
                columns = {
                        @ColumnResult(name = "quantityfn", type = Integer.class),
                        @ColumnResult(name= "datetimefn", type = String.class)

                }
        )
)
@NamedNativeQuery( //Aqui es donde se va a indicar la configuración del alias que se habia colocado en la clase anterior
        name ="Sale.fn_sales", //un alias de configuracion, se encuentra en ISaleRepo
        query = "select * from fn_sales()",
        resultSetMapping = "Procedure.Procedure.DTO"
)

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idSale;

    @ManyToOne
    @JoinColumn(name= "id_client", nullable = false, foreignKey = @ForeignKey(name="Fk_SALE_CLIENT"))
    private Client client;

    @ManyToOne
    @JoinColumn(name= "id_user", nullable = false, foreignKey = @ForeignKey(name="Fk_SALE_USER"))
    private User user;

    @Column(nullable =false)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "decimal(6,2)", nullable = false)
    private double total;

    @Column(columnDefinition = "decimal(6,2)", nullable = false)
    private double tax; //impuesto

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL) //ese sale se relaciona con el sale de la clase saleDetail
    private List<SaleDetail> details; //tema de cascada para que  todo lo que le pase a la cabecera le va a pasar al padre
                                      //ACID
                                      //ATOMICIDAD
                                      //CONSISTENCIA
                                      //AISLAMIENTO
                                     // DURABILIDAD
}
