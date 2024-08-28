package com.mitocode.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable //quiere deccir que podre usar la clase de IngressDetail
@EqualsAndHashCode
public class IngressDetailPK implements Serializable {
    @ManyToOne
    @JoinColumn(name ="id_ingress", nullable = false)
    private Ingress ingress;

    @ManyToOne
    @JoinColumn(name ="id_product", nullable = false)
    private Product product;

    //OBJ1(ING1 PRO1)

}
