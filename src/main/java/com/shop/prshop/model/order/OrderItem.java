package com.shop.prshop.model.order;

import jakarta.persistence.*;

@Entity
@Table(name="order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "order_detail_id")
    private Order order;

    public OrderItem() {

    }

    public OrderItem(Long itemId, int amount) {
        //this.orderDetailId = orderDetailId;
        this.itemId = itemId;
        this.amount = amount;
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
}
