package com.shop.prshop.controller;


import com.shop.prshop.model.Item;
import com.shop.prshop.model.order.Order;
import com.shop.prshop.model.user.User;
import com.shop.prshop.repository.ItemRepository;
import com.shop.prshop.repository.OrderItemRepository;
import com.shop.prshop.repository.OrderRepository;
import com.shop.prshop.repository.UserRepository;
import com.shop.prshop.service.AdminService;
import com.shop.prshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    public final ItemRepository itemRepository;
    public final OrderRepository orderRepository;
    public final UserRepository userRepository;
    public final OrderItemRepository orderItemRepository;
    public final AdminService adminService;
    @Autowired
    public AdminController(ItemRepository itemRepository, OrderRepository orderRepository, UserRepository userRepository, OrderItemRepository orderItemRepository, AdminService adminService) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.adminService = adminService;
    }

    @GetMapping("/additem")
    private String adminPage(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "additem";
    }

    @PostMapping("/save")
    private String addItem(@ModelAttribute("item") Item item) {
        adminService.saveItem(item);

        return "redirect:/acountPage";
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long id, @RequestParam String path) {
        adminService.deleteItem(id);

        return "redirect:" + path;
    }

    @GetMapping("/showUpdateForm/{itemId}")
    public String showUpdateForm(@PathVariable("itemId") Long id, @RequestParam String path, Model model) {

        Optional<Item> optionalItem = itemRepository.findById(id);

        if(optionalItem.isPresent()) {
            Item itemToUpdate = optionalItem.get();
            model.addAttribute("item", itemToUpdate);
        }
        else {
            logger.info("item with id: " + id + "not found!");
        }

        return "updateForm";
    }

    @GetMapping("/showOrders")
    public String showOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "ordersList";
    }

    @GetMapping("/showItems")
    public String showItems(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "itemsList";
    }

    @GetMapping("/showUsers")
    public String showUsers(Model model) {
        List<User> users =  userRepository.findAll();
        model.addAttribute("users", users);
        return "usersList";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") int id, @RequestParam String path) {
        logger.info("Method deleteUser invoked");
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            logger.info("user with id: " + id + "deleted");
        }
        else {
            logger.info("User with id: " + id + "not found!");
        }


        return "redirect:" + path;
    }

    @GetMapping("/showDetails/{itemId}")
    public String showDetails(@PathVariable("itemId") Long id, Model model) {
        model.addAttribute("orderItems", orderItemRepository.findByOrderId(id));
        return "orderDetails";
    }

}
