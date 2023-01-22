package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.HashMap;
import java.util.Map;


public class CheckoutService {

    private Map<Item, Integer> itemsInBasket;

    public CheckoutService() {
        this.itemsInBasket = new HashMap<>();
    }
    public Map<Item, Integer> getItemsInBasket() {
        return itemsInBasket;
    }

    public int getItemQuantity(Item item) {
        return itemsInBasket.getOrDefault(item, 0);
    }

    public void scanItem(Item item) {
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("Price of item cannot be < 0");
        }
        itemsInBasket.merge(item, 1, Integer::sum);
    }
}
