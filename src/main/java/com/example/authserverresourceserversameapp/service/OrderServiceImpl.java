package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.CartItemRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private CartItemRepository cartItemRepository;

    private ProductRepository productRepository;

    public OrderServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItemDto addToCart(CartItemDto dto, User user) {
        CartItem cartItem = null;
        CartItemDto cartItemDto = new CartItemDto();
        Product newProduct = productRepository.findById(dto.getProductId()).get();
        Product cartItemProduct = productRepository.getByItemsId(dto.getCartItemId());

        if (dto.getCartItemId() == 0) {
            cartItem = new CartItem();
            cartItem.setQuantity(1);
            user.addCartItem(cartItem);
            newProduct.addCartItem(cartItem);
        } else if (dto.getCartItemId() > 0) {
            cartItem = cartItemRepository.findById(dto.getCartItemId()).get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        CartItem saved = cartItemRepository.save(cartItem);
        cartItemDto.setCartItemId(saved.getId());
        cartItemDto.setQuantity(saved.getQuantity());
        cartItemDto.setProductId(saved.getProduct().getId());
        return cartItemDto;
    }

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.getAllByUser(user);
    }
}
