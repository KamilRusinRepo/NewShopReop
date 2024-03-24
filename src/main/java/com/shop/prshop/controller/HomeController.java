package com.shop.prshop.controller;

import com.shop.prshop.Cart;
import com.shop.prshop.model.Item;
import com.shop.prshop.repository.ItemRepository;
import com.shop.prshop.service.CartService;
import com.shop.prshop.service.HomeService;
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
    private final CartService cartService;
    private final HomeService homeService;

    @Autowired
    public HomeController(CartService cartService, HomeService homeService) {
        this.cartService = cartService;
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("apple", homeService.findItemsTop6ByMake("Apple"));
        model.addAttribute("samsung", homeService.findItemsTop6ByMake("Samsung"));
        return "home";
    }

    @GetMapping("/applepage")
    public String applePage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForBrandPages("Apple", sort);

        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "applePage";
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
    public String showSamsungPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForBrandPages("Samsung", sort);

        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "samsungPage";
    }

    @GetMapping("/phonesPage")
    public String showPhonesPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForCategoryPages("Phone", sort);
        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "phonesPage";
    }

    @GetMapping("/laptopsPage")
    public String showLaptopsPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForCategoryPages("Laptop", sort);
        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "laptopsPage";
    }

    @GetMapping("/tabletsPage")
    public String showTabletsPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForCategoryPages("Tablet", sort);
        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "tabletsPage";
    }

    @GetMapping("/headphonesPage")
    public String showHeadphonesPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForCategoryPages("Headphones", sort);
        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "headphonesPage";
    }

    @GetMapping("/watchesPage")
    public String showWatchesPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForCategoryPages("Watch", sort);
        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "watchesPage";
    }

    @GetMapping("/accessoriesPage")
    public String showAccessoriesPage(@RequestParam(defaultValue = "none") String sort, Model model) {
        List<Item> items = homeService.findItemsForCategoryPages("Accessories", sort);
        model.addAttribute("items", items);
        model.addAttribute("sort", sort);
        return "accessoriesPage";
    }
}
