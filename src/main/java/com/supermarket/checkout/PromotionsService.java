package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.Map;

public class PromotionsService {

    static int computePriceDiffFromMultiPricePromotion(Map<Item, Integer> productsEligibleForPromotions, Item multipriceItem, int n, int specialPrice) {
        int qty = productsEligibleForPromotions.getOrDefault(multipriceItem, 0);
        int q = qty/ n;
        int r = qty % n;
        int priceDiff = - (multipriceItem.getPrice() * qty) + q * specialPrice + r * multipriceItem.getPrice();
        return priceDiff;
    }

    static int computePriceDiffFor3For1Promotion(Map<Item, Integer> productsEligibleForPromotions, Item buyNget1FreeItem, int n) {
        int qty = productsEligibleForPromotions.getOrDefault(buyNget1FreeItem, 0);
        int q = qty / n;
        int r = qty % n;
        int priceDiff = - (buyNget1FreeItem.getPrice() * qty) + q * (n-1) * buyNget1FreeItem.getPrice() + r * buyNget1FreeItem.getPrice();
        return priceDiff;
    }

    static Map<Item, Integer> excludeItemsIncludedInMultipleNPromotion(Map<Item, Integer> eligibleItemsMap, Item item, int n) {
        int qty = eligibleItemsMap.getOrDefault(item, 0);
        int r = qty % n;
        //eligibleItemsMap.compute(item, (i,v) -> r);
        eligibleItemsMap.put(item, r);
        return eligibleItemsMap;
    }

}
