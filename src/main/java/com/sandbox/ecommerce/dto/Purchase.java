package com.sandbox.ecommerce.dto;

import com.sandbox.ecommerce.entity.Address;
import com.sandbox.ecommerce.entity.Customer;
import com.sandbox.ecommerce.entity.Order;
import com.sandbox.ecommerce.entity.OrderItem;

import lombok.Data;
import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}