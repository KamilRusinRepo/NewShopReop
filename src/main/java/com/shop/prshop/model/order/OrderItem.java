package com.shop.prshop.model.order;

import com.shop.prshop.controller.AdminController;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="order_items")
public class OrderItem {

    private static final Logger logger = LoggerFactory.getLogger(OrderItem.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "sum")
    private BigDecimal price;

    @Column(name = "item_full_name")
    private String itemFullName;

    @Column(name = "item_image")
    private String itemImage;

    @Column(name = "order_date")
    private String orderDate;

    @ManyToOne
    @JoinColumn(name = "order_detail_id")
    private Order order;

    public OrderItem() {

    }

    public OrderItem(Long itemId, int amount, BigDecimal price, String itemFullName, String itemImage) {
        this.itemId = itemId;
        this.amount = amount;
        this.price = price;
        this.itemFullName = itemFullName;
        this.itemImage = itemImage;
        this.orderDate = formatOrderDate(LocalDateTime.now());
    }

    private String formatOrderDate(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getItemFullName() {
        return itemFullName;
    }

    public void setItemFullName(String itemFullName) {
        this.itemFullName = itemFullName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
