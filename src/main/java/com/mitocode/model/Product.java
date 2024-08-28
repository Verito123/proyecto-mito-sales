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
public class Product {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  idProduct;
                    //muchos productos pueden estar en muchos ingresos de mercaderia
    @ManyToOne //FK //muchos productos estan en una categoria
    @JoinColumn(name = "id_category", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY")) //FK_PRODUCT_CATEGORY
    private Category category;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String description;
    // @Column(nullable = false)
    @Column(columnDefinition = "decimal(6,2)", nullable =false)
    private double price;

    @Column(nullable = false)
    private boolean enabled;

}
