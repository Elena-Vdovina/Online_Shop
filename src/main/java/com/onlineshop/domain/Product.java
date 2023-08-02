package com.onlineshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "products")
@Setter
@Getter
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer","nandler"})
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Supplier.class)
    @JoinColumn(name = "supplierId", nullable = false)
    private Supplier supplier;

    private String productName;
    private String description;
    private Float price;
    private Boolean isDeleted;
}
