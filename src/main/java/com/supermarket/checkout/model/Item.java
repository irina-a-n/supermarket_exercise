package com.supermarket.checkout.model;

public class Item {
    private final String sku;
    private final int price;

    public Item(String sku, int price) {
        this.sku = sku;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public int getPrice() {
        return price;
    }


}
