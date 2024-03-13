package com.shop.prshop.controller;

import com.shop.prshop.Cart;
import com.shop.prshop.dto.OrderDto;
import com.shop.prshop.mapper.OrderMapper;
import com.shop.prshop.model.user.User;
import com.shop.prshop.repository.UserRepository;
import com.shop.prshop.service.CartService;
import com.shop.prshop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(CartService cartService, OrderService orderService, UserRepository userRepository) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userRepository = userRepository;
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
    public String showCheckout(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && !authentication.getName().equals("anonymousUser")) {
            String email = authentication.getName();
            logger.info("email: " + email);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.get();
            OrderDto orderDto = OrderMapper.mapToOrderDto(user);
            model.addAttribute("orderDto", orderDto);
        }
        else {
            OrderDto orderDto = new OrderDto();
            model.addAttribute("orderDto", orderDto);
        }
        return "checkout";
    }

    @PostMapping("/saveorder")
    public String saveOrder(OrderDto orderDto) {
        orderService.saveOrder(orderDto);
        return "redirect:/";
    }
}
