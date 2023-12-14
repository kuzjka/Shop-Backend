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

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public OrderServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(CartItemDto dto, User user) {
        CartItem cartItem = null;
        Cart cart = null;
        if (dto.getCartId() == 0 && dto.getCartItemId() == 0) {
            cart = new Cart();
            user.addCart(cart);
            cartItem = new CartItem();
        } else if (dto.getCartId() > 0 && dto.getCartItemId() == 0) {
            cart = cartRepository.findById(dto.getCartId()).get();
            cartItem = new CartItem();

        } else if (dto.getCartId() > 0 && dto.getCartItemId() > 0) {
            cart = cartRepository.findById(dto.getCartId()).get();
            cartItem = cartItemRepository.findById(dto.getCartItemId()).get();
        }

        cart.addCartItem(cartItem);
        Product product = productRepository.findById(dto.getProductId()).get();
        product.addCartItem(cartItem);
        cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        Cart saved = cartRepository.save(cart);

        return saved;

    }

    @Override
    public Cart getCart(User user) {
        return cartRepository.getByUser(user);
    }
}
