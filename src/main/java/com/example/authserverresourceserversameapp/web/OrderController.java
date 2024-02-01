package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.OrderService;
import com.example.authserverresourceserversameapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public Cart getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.getCart(user);
    }

    @PostMapping
    public Cart addCartItem(@RequestBody CartItemDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.addCartItem(dto, user);
    }
    @PutMapping
    public Cart editCartItem(@RequestBody CartItemDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return orderService.editCartItem(dto, user);
    }
    @DeleteMapping
    public long removeCartItem(@RequestParam long itemId) {
        return orderService.removeCartItem(itemId);
    }
}
