package com.sandbox.ecommerce.controller;

import com.sandbox.ecommerce.service.CheckoutService;
import com.sandbox.ecommerce.dto.Purchase;
import com.sandbox.ecommerce.dto.PurchaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }
}