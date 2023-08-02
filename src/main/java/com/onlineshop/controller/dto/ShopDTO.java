package com.onlineshop.controller.dto;

import com.onlineshop.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopDTO {
    private Integer shopId;
    private String shopName;

    public static ShopDTO getInstance(Shop shop){
        return new ShopDTO(shop.getShopId(), shop.getShopName());
    }

}
