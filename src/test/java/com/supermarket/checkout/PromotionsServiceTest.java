package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PromotionsServiceTest {
    @Test
    public void should_ComputeDiffForMultiPricePromotion() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        int n = 2;
        int specialPrice = 125;
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 3, itemC , 1);
        int expected = - 3*75 + 125 + 75;
        int actual = PromotionsService.computePriceDiffFromMultiPricePromotion(map, itemB, n, specialPrice);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ComputeDiffForMultiPricePromotion_When_ExactlyNPromoItems() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        int n = 3;
        int specialPrice = 200;
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 3, itemC , 1);
        int expected = - 3*75 + 200;
        int actual = PromotionsService.computePriceDiffFromMultiPricePromotion(map, itemB, n, specialPrice);
        assertEquals(expected, actual);

    }

    @Test
    public void should_ComputeDiffForMultiPricePromotion_When_MultipleOfN() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        int n = 2;
        int specialPrice = 125;
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 4, itemC , 1);
        int expected = - 4*75 + 125*2 ;
        int actual = PromotionsService.computePriceDiffFromMultiPricePromotion(map, itemB, n, specialPrice);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ComputeDiffForBuyNGet1FreePromotionN() {
        Item itemA = new Item("A", 50);
        Item itemC = new Item("C", 25);
        int n = 3;
        Map<Item, Integer> map = Map.of(itemA, 2,  itemC , 3);
        int expected = - 3*25 + 2 *25 ;
        int actual = PromotionsService.computePriceDiffFor3For1Promotion(map, itemC, n);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ComputeDiffForBuyNGet1FreePromotion_When_MultipleOfN() {
        Item itemA = new Item("A", 50);
        Item itemC = new Item("C", 25);
        int n = 3;
        Map<Item, Integer> map = Map.of(itemA, 2,  itemC , 9);
        int expected = - 9*25 + 2 *25 + 2*25 + 2*25 ;
        int actual = PromotionsService.computePriceDiffFor3For1Promotion(map, itemC, n);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ComputeDiffForBuyNGet1FreePromotion_When_NotMultipleOfN() {
        Item itemA = new Item("A", 50);
        Item itemC = new Item("C", 25);
        int n = 3;
        Map<Item, Integer> map = Map.of(itemA, 2,  itemC , 8);
        int expected = - 8*25 + 2 *25 + 2*25 + 2*25 ;
        int actual = PromotionsService.computePriceDiffFor3For1Promotion(map, itemC, n);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ExcludeItemsIncludedInMultipleNPromotion() {
        Item itemA = new Item("A", 50);
        Item itemC = new Item("C", 25);
        int BUY_N_GET_1_FREE_N = 3;
        Map<Item, Integer> map = new HashMap<>(Map.of(itemA, 2,  itemC , 8));

        Map<Item, Integer> expectedMap = Map.of(itemA, 2, itemC, 2);
        Map<Item, Integer> actualMap = PromotionsService.excludeItemsIncludedInMultipleNPromotion(map, itemC, BUY_N_GET_1_FREE_N);

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void should_ExcludeItemsSuccessively() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        Map<Item, Integer> map = new HashMap<>(Map.of(itemA, 2, itemB, 5 , itemC , 8));
        int MULTIPRICE_N = 4;
        map = PromotionsService.excludeItemsIncludedInMultipleNPromotion(map, itemB, MULTIPRICE_N);
        int BUY_N_GET_1_FREE_N = 3;

        Map<Item, Integer> expectedMap = Map.of(itemA, 2, itemB, 1,  itemC, 2);
        Map<Item, Integer> actualMap = PromotionsService.excludeItemsIncludedInMultipleNPromotion(map, itemC, BUY_N_GET_1_FREE_N);

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void should_ComputeDiffForMealDealPromotion() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        Item itemD = new Item("D", 150);
        Item itemE = new Item("E", 200);
        int MEAL_DEAL_PRICE = 300;
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 4, itemC , 1, itemD, 3, itemE, 2);
        int expected = -3*150 -2*200  + 300 * 2 + 150 ;
        int actual = PromotionsService.computePriceDiffFromMealDeal(map, itemD, itemE, MEAL_DEAL_PRICE);
        assertEquals(expected, actual);

    }

}
