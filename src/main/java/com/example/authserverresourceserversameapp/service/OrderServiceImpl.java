package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Item;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.ItemRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    private final CartRepository cartRepository;

    public OrderServiceImpl(ProductRepository productRepository,
                            ItemRepository itemRepository,
                            CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addItem(ItemDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Item item = null;
        Cart cart = null;
        if (dto.getCartId() == 0 && dto.getItemId() == 0) {
            cart = new Cart();
            item = new Item();
            cart.addItem(item);
            user.addItem(item);
            product.addItem(item);
            item.setQuantity(1);
        } else if (dto.getCartId() > 0 && dto.getItemId() == 0) {
            cart = cartRepository.findById(dto.getCartId()).get();
            item = new Item();
            cart.addItem(item);
            user.addItem(item);
            product.addItem(item);
            item.setQuantity(1);
        } else if (dto.getItemId() > 0 && dto.getItemId() > 0) {
            item = itemRepository.findById(dto.getItemId()).get();
            cart = item.getCart();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        }
        return cartRepository.save(cart);
    }

    @Override
    public List<Item> getUserItems(User user) {
        return itemRepository.getAllByUser(user);
    }


    @Override
    public long deleteItem(long itemId) {
        Item item = itemRepository.findById(itemId).get();
        User user = item.getUser();
        Cart cart = item.getCart();
        user.removeItem(item);
        cart.removeItem(item);
        itemRepository.delete(item);
        return itemId;

    }
}
