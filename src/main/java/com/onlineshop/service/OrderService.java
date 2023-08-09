package com.onlineshop.service;

import com.onlineshop.controller.dto.OrderDTO;
import com.onlineshop.domain.*;
import com.onlineshop.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderDTO createOrder(Integer customerId, Integer shopId, Integer productId) {
        Order order = new Order();
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            Optional<Shop> shop = shopRepository.findById(shopId);
            if (shop.isPresent()) {
                order.setCustomer(customer.get());
                order.setShop(shop.get());
                order.setOrderDate(OffsetDateTime.now());
                order.setState(OrderState.NEW);
                order = orderRepository.save(order);
                log.info("Order created orderId: {}", order.getOrderId());
                Optional<Product> product = productRepository.findById(productId);
                if (product.isPresent()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(product.get());
                    orderDetail.setQuantity(1);
                    orderDetailRepository.save(orderDetail);
                    log.info("Order with orderDetail created orderId: {}", order.getOrderId());
                    return OrderDTO.getInstance(order);
                }
                log.error("Not found Product for created orderDetail productId: {}", productId);
                return null;
            }
            log.error("Not found Shop for created Order shopId: {}", shopId);
            return null;
        }
        log.error("Not found Customer for created Order customerId: {}", customerId);
        return null;
    }

    public OrderDTO addProduct(Integer orderId, Integer productId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                List<OrderDetail> orderList = orderDetailRepository.findByOrder(order.get());
                boolean productFound = false;
                for (OrderDetail orderDetail : orderList) {
                    if (orderDetail.getProduct().equals(product.get())) {
                        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
                        orderDetailRepository.save(orderDetail);
                        productFound = true;
                        log.info("Quantity of Product +1 productId: {}",productId);
                        break;
                    }
                }
                if (!productFound) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order.get());
                    orderDetail.setProduct(product.get());
                    orderDetail.setQuantity(1);
                    orderDetailRepository.save(orderDetail);
                    log.info("Product added to orderDetail productId: {}", productId);
                }
                return OrderDTO.getInstance(order.get());
            }
            log.error("Not found Product for add to orderDetail productId: {}", productId);
            return null;
        }
        log.error("Not found Order for add Product to orderDetail orderId: {}", orderId);
        return null;
    }

    public OrderDTO deleteProduct(Integer orderId, Integer productId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                List<OrderDetail> orderList = orderDetailRepository.findByOrder(order.get());
                boolean productFound = false;
                for (OrderDetail orderDetail : orderList) {
                    if (orderDetail.getProduct().equals(product.get())) {
                        orderDetail.setQuantity(orderDetail.getQuantity() - 1);
                        orderDetailRepository.save(orderDetail);
                        productFound = true;
                        log.info("Quantity of Product -1 productId: {}",productId);
                        break;
                    }
                }
                if (!productFound) {
                    log.error("Not found Product in orderDetail for delete from orderDetail productId: {}", productId);
                    return null;
                }
                return OrderDTO.getInstance(order.get());
            }
            log.error("Not found Product for delete from orderDetail productId: {}", productId);
            return null;
        }
        log.error("Not found Order for delete from orderDetail orderId: {}", orderId);
        return null;
    }

    public OrderDTO sendOrder(Integer orderId) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if(optOrder.isPresent()) {
            Order order = optOrder.get();
            if (order.getState().equals(OrderState.NEW)) {
                order.setState(OrderState.SENT);
                order = orderRepository.save(order);
                log.info("Order state changed for 'SENT' orderId: {}", orderId);
                return OrderDTO.getInstance(order);
            }
            log.error("Order state can't changed to 'SENT' orderId: {}", orderId);
            return null;
        }
        log.error("Not found Order for change state orderId: {}", orderId);
        return null;
    }
}
