package com.onlineshop.controller;

import com.onlineshop.controller.dto.OrderDTO;
import com.onlineshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{customerId}/{shopId}/{productId}")
    public OrderDTO createOrder(@PathVariable Integer customerId,
                                @PathVariable Integer shopId,
                                @PathVariable Integer productId) {
        return orderService.createOrder(customerId, shopId, productId);
    }

    @PutMapping("/add/{orderId}/{productId}")
    public OrderDTO addProduct(@PathVariable Integer orderId, @PathVariable Integer productId){
        return orderService.addProduct(orderId, productId);
    }

    @DeleteMapping("/delete/{orderId}/{productId}")
    public OrderDTO deleteProduct(@PathVariable Integer orderId, @PathVariable Integer productId){
        return orderService.deleteProduct(orderId, productId);
    }

    @PutMapping("/sendState/{orderId}")
    public OrderDTO updateOrder(@PathVariable Integer orderId){
        return orderService.sendOrder(orderId);
    }

}
