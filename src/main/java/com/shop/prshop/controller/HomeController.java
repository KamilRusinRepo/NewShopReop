package com.shop.prshop.controller;

import com.shop.prshop.Cart;
import com.shop.prshop.model.Item;
import com.shop.prshop.repository.ItemRepository;
import com.shop.prshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.hibernate.query.spi.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final CartService cartService;
    private final ItemRepository itemRepository;

    @Autowired
    public HomeController(CartService cartService, ItemRepository itemRepository) {
        this.cartService = cartService;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("apple", itemRepository.findTop7ByMake("Apple").stream().limit(6).toList());
        model.addAttribute("samsung", itemRepository.findTop7ByMake("Samsung").stream().limit(6).toList());
        return "home";
    }

    @GetMapping("/applepage")
    public String applePage(Model model) {
        List<Item> items = itemRepository.findByMake("Apple");
        model.addAttribute("items", items);
        return "applepage";
    }

    @GetMapping("/add/{itemId}")
    public String addItem(@PathVariable("itemId") Long itemId, Model model, @RequestParam String path) {
        cartService.addItemToCart(itemId);
        model.addAttribute("items", cartService.getAllItems());

        return "redirect:" + path;
    }

    @GetMapping("/acountPage")
    public String showAcountPage() {
        return "acountpage";
    }

    @GetMapping("/productPage")
    public String showProductPage() {
        return "productPage";
    }

    @GetMapping("/samsungPage")
    public String showSamsungPage(Model model) {
        List<Item> items = itemRepository.findByMake("Samsung");
        model.addAttribute("items", items);
        return "samsungPage";
    }

    @GetMapping("/phonesPage")
    public String showPhonesPage(Model model) {
        List<Item> items = itemRepository.findByCategory("Phone");
        model.addAttribute("items", items);
        return "phonesPage";
    }

    @GetMapping("/laptopsPage")
    public String showLaptopsPage(Model model) {
        List<Item> items = itemRepository.findByCategory("Laptop");
        model.addAttribute("items", items);
        return "laptopsPage_style";
    }

    @GetMapping("/tabletsPage")
    public String showTabletsPage(Model model) {
        List<Item> items = itemRepository.findByCategory("Tablet");
        model.addAttribute("items", items);
        return "tabletsPage";
    }

    @GetMapping("/headphonesPage")
    public String showHeadphonesPage(Model model) {
        List<Item> items = itemRepository.findByCategory("Headphones");
        model.addAttribute("items", items);
        return "headphonesPage";
    }

    @GetMapping("/watchesPage")
    public String showWatchesPage(Model model) {
        List<Item> items = itemRepository.findByCategory("Watch");
        model.addAttribute("items", items);
        return "watchesPage";
    }

    @GetMapping("/accessoriesPage")
    public String showAccessoriesPage(Model model) {
        List<Item> items = itemRepository.findByCategory("Accessories");
        model.addAttribute("items", items);
        return "accessoriesPage";
    }
}
