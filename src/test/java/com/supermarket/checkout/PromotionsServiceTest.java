package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PromotionsServiceTest {
    @Test
    public void should_ComputeDiffForMultiPricePromotion() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("B", 25);
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 3, itemC , 1);
        //  MULTIPRICE_N = 2;
        //  MUTLIPRICE_PRICE = 125;
        int expected = - 3*75 + 125 + 75;
        int actual = PromotionsService.computePriceDiffFromMultiPricePromotion(map, itemB);
        assertEquals(expected, actual);

    }

    @Test
    public void should_ComputeDiffForMultiPricePromotionWhenMultipleOfN() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("B", 25);
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 4, itemC , 1);
        int expected = - 4*75 + 125*2 ;
        int actual = PromotionsService.computePriceDiffFromMultiPricePromotion(map, itemB);
        assertEquals(expected, actual);

    }
}
