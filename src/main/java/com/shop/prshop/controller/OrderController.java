package com.shop.prshop.controller;

import com.shop.prshop.Cart;
import com.shop.prshop.dto.OrderDto;
import com.shop.prshop.service.CartService;
import com.shop.prshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public OrderController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getCartView() {
        return "cartview";
    }


    @GetMapping("/increase/{itemId}")
    public String increaseItem(@PathVariable("itemId") Long itemId) {
        cartService.addItemToCart(itemId);
        return "cartview";
    }

    @GetMapping("/decrease/{itemId}")
    public String decreaseItem(@PathVariable("itemId") Long itemId) {
        cartService.decreaseItemFromCart(itemId);
        return "cartview";
    }

    @GetMapping("/removeItem/{itemId}")
    public String removeItem(@PathVariable("itemId") Long itemId) {
        cartService.removeItemFromCart(itemId);
        return "cartview";
    }

    @GetMapping("/checkout")
    public String showCheckout() {
        return "checkout";
    }

    @PostMapping("/saveorder")
    public String saveOrder(OrderDto orderDto) {
        orderService.saveOrder(orderDto);
        return "redirect:/";
    }
}
