package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.CartItemRepository;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart addToCart(CartItemDto dto, User user) {
        CartItem cartItem;
        boolean newItem = true;
        Cart cart = cartRepository.getByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        Product product = productRepository.findById(dto.getProductId()).get();
        List<CartItem> items = new ArrayList<>(cart.getItems());
        for (CartItem item : items) {
            if (item.getProduct().getId() == dto.getProductId()) {
                cartItem = item;
                if (cartItem.getQuantity() == 1 && dto.getQuantity() < 0) {
                    cartItem.setQuantity(1);
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
                }
                newItem = false;
            }
        }
        if (newItem) {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            product.addCartItem(cartItem);
            cart.addCartItem(cartItem);
        }
        return cartRepository.save(cart);
    }

    @Override
    public long removeFromCart(long productId) {
        Product product = productRepository.findById(productId).get();
        CartItem cartItem = cartItemRepository.getByProduct(product);
        Cart cart = cartItem.getCart();
        product.removeCartItem(cartItem);
        long id = cartItem.getId();
        cartItemRepository.delete(cartItem);
        if (cart.getItems().size() == 0) {
            cartRepository.delete(cart);
        }
        return id;
    }

    @Override
    public Cart getCart(User user) {
        return cartRepository.getByUser(user);
    }
}
