package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.HashMap;
import java.util.Map;


public class CheckoutService {

    private Map<Item, Integer> itemsInBasket;
    private static final int MULTIPRICE_N = 2;
    private static final int MULTIPRICE_PRICE = 125;
    private static final Item multiPriceItem = new Item("B", 75);

    private static final int BUY_N_GET_1_FREE_N = 3;
    private static final Item buy3get1FreeItem = new Item("C", 25);
    private static final Item item1MealDeal = new Item("D", 150);
    private static final Item item2MealDeal = new Item("E", 200);

    private static final int MEAL_DEAL_PRICE = 300;


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

    public int computeFinalPrice() {
        Map<Item, Integer> itemsEligibleForPromotion = new HashMap<>(itemsInBasket);
        int priceWithoutPromotions = Computation.computeTotalPrice(itemsInBasket);

        int multiPricePromotionsDiff = PromotionsService.computePriceDiffFromMultiPricePromotion(getItemsInBasket(), multiPriceItem, MULTIPRICE_N, MULTIPRICE_PRICE);
        itemsEligibleForPromotion = PromotionsService.excludeItemsIncludedInMultipleNPromotion(itemsEligibleForPromotion, multiPriceItem, MULTIPRICE_N);

        int buyNget1FreePromotionDiff = PromotionsService.computePriceDiffFor3For1Promotion(itemsEligibleForPromotion, buy3get1FreeItem, BUY_N_GET_1_FREE_N);
        itemsEligibleForPromotion = PromotionsService.excludeItemsIncludedInMultipleNPromotion(itemsEligibleForPromotion, buy3get1FreeItem, BUY_N_GET_1_FREE_N);

        int mealDealDiff = PromotionsService.computePriceDiffFromMealDeal(itemsEligibleForPromotion, item1MealDeal, item2MealDeal, MEAL_DEAL_PRICE);
        itemsEligibleForPromotion = PromotionsService.excludeMealDealItems(itemsEligibleForPromotion, item1MealDeal, item2MealDeal);

        int totalPriceWithPromotionsApplied = priceWithoutPromotions + multiPricePromotionsDiff + buyNget1FreePromotionDiff + mealDealDiff;
        return totalPriceWithPromotionsApplied;
    }
}
