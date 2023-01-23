package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.Map;
import java.util.function.BiFunction;

public class Computation {
    private static BiFunction<Item, Integer, Integer> computeCost = (item, qty) -> item.getPrice() * qty;
    public static int computeTotalPrice(Map<Item, Integer> mapItemQty) {
        return mapItemQty.entrySet().stream()
                .map(x -> computeCost.apply(x.getKey(), x.getValue()))
                .reduce(0, Integer::sum);
    }

}
