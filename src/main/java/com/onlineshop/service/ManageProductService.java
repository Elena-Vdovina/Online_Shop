package com.onlineshop.service;

import com.onlineshop.controller.dto.OrderDTO;
import com.onlineshop.controller.dto.ProductInShopDTO;
import com.onlineshop.domain.*;
import com.onlineshop.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ManageProductService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInShopRepository productInShopRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public ProductInShopDTO productToShop(Integer shopId, ProductInShopDTO products) {
        Optional<Shop> optShop = shopRepository.findById(shopId);
        if (optShop.isPresent()) {
            List<ProductInShop> productsInShop = productInShopRepository.findByShop(optShop.get());
            for (ProductInShopDTO.ProductItem productItem : products.getProductItems()) {
                Optional<Product> optProduct = productRepository.findById(productItem.getProductId());
                if (optProduct.isPresent()) {
                    boolean foundProduct = false;
                    for (ProductInShop productInShop : productsInShop) {
                        if (productInShop.getProduct().equals(optProduct.get())) {
                            productInShop.setQuantity(productInShop.getQuantity() + productItem.getQuantity());
                            productInShop = productInShopRepository.save(productInShop);
                            log.info("Product add quantity to shop productId: {}", productInShop.getProduct().getProductId());
                            foundProduct = true;
                            break;
                        }
                    }
                    if (!foundProduct) {
                        ProductInShop productInShop = new ProductInShop();
                        productInShop.setShop(optShop.get());
                        productInShop.setProduct(optProduct.get());
                        productInShop.setQuantity(productItem.getQuantity());
                        productInShop = productInShopRepository.save(productInShop);
                        log.info("Product add quantity to shop productId: {}", productInShop.getProduct().getProductId());
                        productsInShop.add(productInShop);
                    }
                }
                log.error("Not found Product for add to shop productId: {}", productItem.getProductId());
                return null;
            }
            productInShopRepository.saveAll(productsInShop);
            return products;

        }
        log.error("Not found Shop shopId: {}", shopId);
        return null;
    }

    public ProductInShopDTO findByShop(Integer shopId) {
        Optional<Shop> optShop = shopRepository.findById(shopId);
        if (optShop.isPresent()) {
            List<ProductInShop> productsInShop = productInShopRepository.findByShop(optShop.get());
            ProductInShopDTO productInShopDTO = new ProductInShopDTO();
            for (ProductInShop product : productsInShop) {
                productInShopDTO.addProductItem(product.getProduct().getProductId(), product.getQuantity());
            }
            log.info("List of Product in Shop shopId: {}", shopId);
            return productInShopDTO;
        }
        log.error("Not found Shop shopId: {}", shopId);
        return null;
    }

    public OrderDTO changeOrderState(Integer orderId, OrderState orderState) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            if (orderState.equals(OrderState.PAID) && order.getState().equals(OrderState.SENT) ||
                    orderState.equals(OrderState.CANCELED)) {
                order.setState(orderState);
                order = orderRepository.save(order);
                log.info("State of Order changed orderId: {}", orderId);
                return OrderDTO.getInstance(order);
            }
            log.error("Can't change state Order orderId: {}", order.getOrderId());
        }
        log.error("Not found Order orderId: {}", orderId);
        return null;
    }

    public OrderDTO getProductsByOrder(Integer orderId) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            if (order.getState().equals(OrderState.PAID)) {
                List<ProductInShop> productsInShop = productInShopRepository.findByShop(order.getShop());
                List<OrderDetail> productsInOrder = orderDetailRepository.findByOrder(order);

                // todo optimization : create map<productId, productInShop>
                for (OrderDetail orderDetail : productsInOrder) {
                    boolean foundProduct = false;
                    for (ProductInShop productInShop : productsInShop) {
                        if (orderDetail.getProduct().equals(productInShop.getProduct())) {
                            productInShop.setQuantity(productInShop.getQuantity() - orderDetail.getQuantity());
                            if (productInShop.getQuantity() < 0) {
                                log.error("Negative quantity Product in shop productId: {}", productInShop.getProduct().getProductId());
                            }
                            log.info("Quantity Product in shop changed productId: {}", productInShop.getProduct().getProductId());
                            productInShopRepository.save(productInShop);
                            foundProduct = true;
                            break;
                        }
                    }
                    if (!foundProduct) {
                        log.error("Not found Product in shop productId: {}", orderDetail.getProduct().getProductId());
                        return null;
                    }
                }
                order.setState(OrderState.DELIVERED);
                order = orderRepository.save(order);
                log.info("State of Order changed for DELIVERED orderId: {}", order.getOrderId());
                return OrderDTO.getInstance(order);
            }
            log.error("Order not paid orderId: {}", order.getOrderId());
            return null;
        }
        log.error("Not found Order orderId: {}", orderId);
        return null;
    }

}
