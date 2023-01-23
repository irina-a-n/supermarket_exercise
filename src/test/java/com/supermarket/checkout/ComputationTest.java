package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ComputationTest {

    @Test
    public void should_ComputeTotalPriceAsSum_Given_PriceAndQuantity() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("B", 25);
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 3, itemC , 1);
        int expectedTotalPrice = 350;
        int actualTotalPrice = Computation.computeTotalPrice(map);
        assertEquals(expectedTotalPrice, actualTotalPrice);

    }

    @Test
    public void should_ComputePriceOfItemsWithQuantityDecreasedByMinQty() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("B", 25);
        Map<Item, Integer> map = Map.of(itemA, 2, itemB, 3, itemC , 1);
        int minQty = 1;
        int expectedTotalPrice = 1*50 + 2*75 + 0*25 ;
        int actualTotalPrice = Computation.computePriceWhenQtyDecreasedByN(map, minQty);
        assertEquals(expectedTotalPrice, actualTotalPrice);

    }
}
