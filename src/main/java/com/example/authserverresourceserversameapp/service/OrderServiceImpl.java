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
    public Item addItem(ItemDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Item item;
        Cart cart;
        if (dto.getItemId() <= 0) {
            item = new Item();
            cart = new Cart();
            cart.addItem(item);
            user.addItem(item);
            product.addItem(item);
            item.setQuantity(1);
        } else {
            item = itemRepository.findById(dto.getItemId()).get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        }
        return itemRepository.save(item);
    }


    @Override
    public List<Item> getItem(User user) {
        return itemRepository.getByUser(user);
    }

    @Override
    public List<Cart> getUserCarts(User user) {
        return cartRepository.findAll();
    }


//    @Override
//    public long addOrder(CartDto dto) {
//        Cart cart = new Cart();
//        cart.setName("cart");
//        Item item = itemRepository.findById(dto.getCart().getId()).get();
//        cart.addCart(item);
//        return cartRepository.save(cart).getId();
//    }

    @Override
    public long deleteItem(long itemId) {

        itemRepository.deleteById(itemId);
        return itemId;
    }
}
