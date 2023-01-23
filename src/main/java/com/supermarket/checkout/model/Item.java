package com.supermarket.checkout.model;

import java.util.Objects;

public class Item {
    private final String sku;
    private final int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return price == item.price && Objects.equals(sku, item.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, price);
    }

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
