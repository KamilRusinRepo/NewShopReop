package com.shop.prshop.controller;


import com.shop.prshop.model.Item;
import com.shop.prshop.model.order.Order;
import com.shop.prshop.repository.ItemRepository;
import com.shop.prshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.event.ItemEvent;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public final ItemRepository itemRepository;
    public final OrderRepository orderRepository;

    @Autowired
    public AdminController(ItemRepository itemRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/additem")
    private String adminPage(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "additem";
    }

    @PostMapping("/save")
    private String addItem(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        return "redirect:/";
    }

    @GetMapping("/showorders")
    @ResponseBody
    public List<Order> showOrders() {
        return orderRepository.findAll();
    }

}
