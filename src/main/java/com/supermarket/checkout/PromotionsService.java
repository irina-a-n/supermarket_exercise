package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.Map;

public class PromotionsService {
    private static final int MULTIPRICE_N = 2;
    private static final int MUTLIPRICE_PRICE = 125;

    public static int computePriceDiffFromMultiPricePromotion(Map<Item, Integer> productsEligibleForPromotions, Item multipriceItem) {
        int qty = productsEligibleForPromotions.getOrDefault(multipriceItem, 0);
        int q = qty/ MULTIPRICE_N;
        int r = qty % MULTIPRICE_N;
        int priceDiff = - (multipriceItem.getPrice() * qty) + q * MUTLIPRICE_PRICE + r * multipriceItem.getPrice();
        return priceDiff;
    }
}
