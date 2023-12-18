package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.CartItemRepository;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private CartItemRepository cartItemRepository;

    private ProductRepository productRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addToCart(CartItemDto dto, User user) {
        CartItem cartItem;
        Product newProduct = productRepository.findById(dto.getProductId()).get();
        Cart cart = cartRepository.getByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        List<CartItem> items = new ArrayList<>(cart.getItems());
        for (CartItem item : items) {
            if (item.getProduct().getId() == dto.getProductId()) {
                dto.setCartItemId(item.getId());
            }
        }
        if (dto.getCartItemId() == 0) {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            newProduct.addCartItem(cartItem);
            cart.addCartItem(cartItem);

        } else if (dto.getCartItemId() > 0) {
            cartItem = cartItemRepository.findById(dto.getCartItemId()).get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        return cartRepository.save(cart);
    }
    @Override
    public Cart getCart(User user) {
        return cartRepository.getByUser(user);
    }
}
