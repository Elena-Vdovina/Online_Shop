package com.onlineshop.repository;

import com.onlineshop.domain.ProductInShop;
import com.onlineshop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInShopRepository extends JpaRepository<ProductInShop, Integer> {
    List<ProductInShop> findByShop(Shop shop);
}
