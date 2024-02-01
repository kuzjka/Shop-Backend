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

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart addCartItem(CartItemDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Cart cart = cartRepository.getByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        CartItem item = new CartItem();
        item.setQuantity(1);
        cart.addCartItem(item);
        product.addCartItem(item);
        return cartRepository.save(cart);
    }
    @Override
    public Cart editCartItem(CartItemDto dto, User user) {
        Cart cart = cartRepository.getByUser(user);
        CartItem item = cartItemRepository.findById(dto.getItemId()).get();

        if (item.getQuantity() == 1 && dto.getQuantity() < 0) {
            item.setQuantity(1);
        } else {
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        }
        return cartRepository.save(cart);
    }
    @Override
    public long removeCartItem(long productId) {
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
