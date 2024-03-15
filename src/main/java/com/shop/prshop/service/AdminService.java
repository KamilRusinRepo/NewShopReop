package com.shop.prshop.service;

import com.shop.prshop.controller.AdminController;
import com.shop.prshop.model.Item;
import com.shop.prshop.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    Logger logger = LoggerFactory.getLogger(AdminService.class);
    public ItemRepository itemRepository;

    @Autowired
    public AdminService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isPresent()) {
            Item itemToDel = optionalItem.get();
            itemRepository.delete(itemToDel);
        }
        else {
            logger.info("User with id: " + id + "not found!");
        }
    }
}
