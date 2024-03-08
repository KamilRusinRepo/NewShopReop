package com.shop.prshop.service;

import com.shop.prshop.model.Item;
import com.shop.prshop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    public final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }



    public void deleteItem(Long id) {
        Optional <Item> itemOptional = itemRepository.findById(id);

        if(itemOptional.isPresent()) {
            Item itemToDel = itemOptional.get();
            itemRepository.delete(itemToDel);
        }
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
