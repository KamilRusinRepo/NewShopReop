package com.shop.prshop.repository;

import com.shop.prshop.model.Item;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.make = ?1")
    List<Item> findByMake(String make);

    @Query("SELECT i FROM Item i WHERE i.type = ?1")
    List<Item> findByCategory(String category);

    @Query("SELECT i FROM Item i WHERE i.make = ?1")
    List<Item> findTop7ByMake(String make);
}
