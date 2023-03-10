package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.*;
import java.util.stream.Collectors;

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
        eligibleItemsMap.put(item, r);
        return eligibleItemsMap;
    }

    static int computePriceDiffFromMealDeal(Map<Item, Integer> eligibleItemsMap, Item item1MealDeal, Item item2MealDeal, int mealDealPrice) {
        int qty1 = eligibleItemsMap.getOrDefault(item1MealDeal, 0);
        int qty2 = eligibleItemsMap.getOrDefault(item2MealDeal, 0);
        int noMealDeals = Math.min(qty1, qty2);
        int priceDiff = - Computation.computeTotalPrice(Map.of(item1MealDeal, qty1, item2MealDeal, qty2)) + noMealDeals * mealDealPrice
                + (qty1 - noMealDeals) * item1MealDeal.getPrice() + (qty2 - noMealDeals) * item2MealDeal.getPrice();
        return priceDiff;
    }

    static Map<Item, Integer> excludeMealDealItems(Map<Item, Integer> eligibleItemsMap, Item item1MealDeal, Item item2MealDeal) {
        int qty1 = eligibleItemsMap.getOrDefault(item1MealDeal, 0);
        int qty2 = eligibleItemsMap.getOrDefault(item2MealDeal, 0);
        int noMealDeals = Math.min(qty1, qty2);
        eligibleItemsMap.put(item1MealDeal, qty1- noMealDeals);
        eligibleItemsMap.put(item2MealDeal, qty2 - noMealDeals);
        return eligibleItemsMap;
    }

    static int computePriceDiffFromMealDealList(Map<Item, Integer> eligibleItemsMap, Set<Item> itemsInMealDeal, int mealDealPrice) {
        if (eligibleItemsMap.keySet().containsAll(itemsInMealDeal)) {
            int noMealDeals = Collections.min(eligibleItemsMap.keySet().stream().filter(x -> itemsInMealDeal.contains(x))
                    .map(x -> eligibleItemsMap.get(x)).collect(Collectors.toSet()));
            Map<Item, Integer> mapOfMealDealItems = eligibleItemsMap.keySet().stream().filter(x -> itemsInMealDeal.contains(x))
                    .collect(Collectors.toMap(x -> x, eligibleItemsMap::get));

            int priceDiff = -Computation.computeTotalPrice(mapOfMealDealItems) + noMealDeals * mealDealPrice
                    + Computation.computePriceWhenQtyDecreasedByN(mapOfMealDealItems, noMealDeals);
            return priceDiff;
        }
        else return 0;
    }

    static Map<Item, Integer> excludeMealDealItemsList(Map<Item, Integer> eligibleItemsMap, Set<Item> itemsInMealDeal) {
        if (eligibleItemsMap.keySet().containsAll(itemsInMealDeal)) {
            int noMealDeals = Collections.min(eligibleItemsMap.values());
            itemsInMealDeal.forEach(item -> eligibleItemsMap.compute(item, (k, v) -> v - noMealDeals));
        }
            return eligibleItemsMap;

    }

}