package com.sandbox.ecommerce.service;

import com.sandbox.ecommerce.dao.CustomerRepository;
import com.sandbox.ecommerce.dto.Purchase;
import com.sandbox.ecommerce.dto.PurchaseResponse;
import com.sandbox.ecommerce.entity.Order;
import com.sandbox.ecommerce.entity.OrderItem;
import com.sandbox.ecommerce.entity.Customer;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        String theEmail = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(theEmail);
        if (customerFromDB != null) {
            // we found then ... let's assign them accordingly
            customer = customerFromDB;
        }

        customer.add(order);

        // save to database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }
    
    private String generateOrderTrackingNumber() {
        /**
         * generate a random UUID number (UUID version-4)
         * for details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
         */
        return UUID.randomUUID().toString();
    }
}