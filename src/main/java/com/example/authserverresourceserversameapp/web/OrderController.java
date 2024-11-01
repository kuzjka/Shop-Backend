package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.CartDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
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
    public List<Cart> getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getCart(user);
    }

    @GetMapping("/order")
    public List<Order> getOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getUserOrders(user);
    }

    @PostMapping("/order")
    public long addOrder(@RequestBody OrderDto dto, Principal principal) {
        long orderId = orderService.addOrder(dto);
        return orderId;
    }

    @PostMapping
    public Cart addCartItem(@RequestBody CartDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addCart(dto, user);
    }

    @DeleteMapping
    public long deleteCart(@RequestParam long itemId) {
        return orderService.deleteCart(itemId);
    }
}
