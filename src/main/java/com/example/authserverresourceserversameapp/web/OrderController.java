package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Order;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.OrderService;
import com.example.authserverresourceserversameapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public Cart getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getUserCart(user);
    }

    @GetMapping("/order")
    public List<Order> getOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getOrders(user);
    }

    @PostMapping
    public Cart addItem(@RequestBody ItemDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addItem(dto, user);
    }

    @PostMapping("/order")
    public long addOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addOrder(user);
    }

    @PutMapping
    public Cart editItem(@RequestBody ItemDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addItem(dto, user);
    }

    @DeleteMapping
    public long deleteItem(@RequestParam long itemId) {
        return orderService.deleteItem(itemId);
    }
}
