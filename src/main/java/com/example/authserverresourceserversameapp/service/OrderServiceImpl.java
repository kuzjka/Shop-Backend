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

    /**
     * adds new item to cart
     *
     * @param dto  dto for adding new item or editing existing item
     * @param user current user
     * @return cart with added new item
     */
    @Override
    public Cart addItem(ItemDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Item item;
        Cart cart = null;
        if (dto.getCartId() == 0 && dto.getItemId() == 0) {
            cart = new Cart();
            item = new Item();
            cart.addItem(item);
            cart.setUser(user);
            product.addItem(item);
            item.setQuantity(1);
        } else if (dto.getCartId() > 0 && dto.getItemId() == 0) {
            cart = cartRepository.findById(dto.getCartId()).get();
            item = new Item();
            cart.addItem(item);
            product.addItem(item);
            item.setQuantity(1);
        }
        return cartRepository.save(cart);
    }

    /**
     * edits existing item in cart
     *
     * @param dto dto for adding new item or editing existing item
     * @return cart with edited existing item
     */
    @Override
    public Cart editItem(ItemDto dto) {
        Item item = itemRepository.findById(dto.getItemId()).get();
        Cart cart = item.getCart();
        if (item.getQuantity() == 1 && dto.getQuantity() == -1) {
            item.setQuantity(1);
        } else {
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        }
        return cartRepository.save(cart);
    }

    /**
     * returns cart created by current user
     *
     * @param user current user
     * @return cart created by current user
     */
    @Override
    public Cart getUserCart(User user) {
        return cartRepository.getByUser(user);
    }

    /**
     * deletes item from cart
     *
     * @param itemId id of item to delete
     * @return id of deleted item
     */
    @Override
    public long deleteItem(long itemId) {
        Item item = itemRepository.findById(itemId).get();
        Cart cart = item.getCart();
        cart.removeItem(item);
        itemRepository.delete(item);
        if (cart.getItems().isEmpty()) {
            cartRepository.delete(cart);
        }
        return itemId;
    }
}
