package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@IdClass(IngressDetailPK.class)
public class IngressDetail {

    @Id
    private Ingress ingress; //llave primaria compuesta entre las 2 clases

    @Id
    private Product product; //llave primaria

    @Column(nullable = false)
    private short quantity;

    @Column(columnDefinition = "decimal(6,2)", nullable = false)
    private double cost;


}


