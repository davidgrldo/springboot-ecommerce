package com.sandbox.ecommerce.service;

import com.sandbox.ecommerce.dto.Purchase;
import com.sandbox.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}