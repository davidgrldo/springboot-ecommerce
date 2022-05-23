package com.sandbox.ecommerce.entity;

import java.util.Set;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    // @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
