package com.onlineshop.controller;

import com.onlineshop.controller.dto.ShopDTO;
import com.onlineshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/shop")
public class AdminShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/all")
    public List<ShopDTO> findAllShop() {
        return shopService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> findByIdShop(@PathVariable Integer id) {
        ShopDTO shop = shopService.findById(id);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shop);
    }

    @PostMapping("/add")
    public ResponseEntity<ShopDTO> addShop(@RequestBody ShopDTO shopDTO) {
        ShopDTO shop = shopService.add(shopDTO);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer id, @RequestBody ShopDTO shopDTO) {
        ShopDTO shop = shopService.update(id, shopDTO);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shop);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ShopDTO> deleteShop(@PathVariable Integer id) {
        ShopDTO shop = shopService.delete(id);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shop);
    }

}
