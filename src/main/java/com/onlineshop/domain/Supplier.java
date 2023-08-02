package com.onlineshop.domain;

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

@Entity(name = "suppliers")
@Setter
@Getter
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer supplierId;

    private String supplierName;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
    @JoinColumn(name = "countryId", nullable = false)
    private Country country;

}
