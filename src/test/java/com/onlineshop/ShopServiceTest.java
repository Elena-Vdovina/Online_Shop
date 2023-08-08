package com.onlineshop;

import com.onlineshop.controller.dto.ShopDTO;
import com.onlineshop.service.ShopService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopServiceTest {

    @Autowired
    private ShopService shopService;

    @Test
    @Order(1)
    public void findAllTest() {
        ShopDTO shopFilial1 = new ShopDTO(null, "Filial 1");
        shopService.add(shopFilial1);
        ShopDTO shopFilial2 = new ShopDTO(null, "Filial 2");
        shopService.add(shopFilial2);

        List<ShopDTO> shops = shopService.findAll();

        Assertions.assertEquals(2, shops.size());
    }

    @Test
    @Order(2)
    public void findByIdTest() {
        ShopDTO shopFound = shopService.findById(1);

        Assertions.assertEquals("Filial 1", shopFound.getShopName());
    }

    @Test
    @Order(3)
    public void addTest() {
        ShopDTO shopFilial3 = new ShopDTO(null, "Filial 3");
        ShopDTO shopAdded = shopService.add(shopFilial3);

        Assertions.assertEquals("Filial 3", shopFilial3.getShopName());
    }

    @Test
    @Order(4)
    public void updateTest() {
        ShopDTO shopFilial4 = new ShopDTO(null, "Filial 4");
        ShopDTO shopUpdated = shopService.update(3, shopFilial4);

        Assertions.assertEquals("Filial 4", shopFilial4.getShopName());
    }

    @Test
    @Order(5)
    public void deleteTest() {
        ShopDTO shopDeleted = shopService.delete(3);

        Assertions.assertEquals("Filial 4", shopDeleted.getShopName());
    }
}
