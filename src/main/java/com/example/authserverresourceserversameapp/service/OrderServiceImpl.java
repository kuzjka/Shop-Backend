package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartDto;

import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.*;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.OrderRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

private final OrderRepository orderRepository;

    public OrderServiceImpl(ProductRepository productRepository,
                            CartRepository cartRepository,
                            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Cart addCart(CartDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Cart cart;
        if (dto.getCartId()==0) {
            cart = new Cart();
            cart.setUser(user);
            cart.addProduct(product);
            cart.setQuantity(1);
        }else { cart = cartRepository.findById(dto.getCartId()).get();

        cart.setQuantity(cart.getQuantity()+1);
        }

        return cartRepository.save(cart);
    }


    @Override
    public List<Cart> getCart(User user) {
        return cartRepository.getByUser(user);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepository.findAll();
    }


    @Override
    public long addOrder(OrderDto dto) {
        Order order = new Order();

        String uuid = UUID.randomUUID().toString();
        order.setUuid(uuid);
        order.setName("order");
        Cart cart = cartRepository.findById(dto.getCart().getId()).get();
        order.addCart(cart);
        return orderRepository.save(order).getId();
    }

}
