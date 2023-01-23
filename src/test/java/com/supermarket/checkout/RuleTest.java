package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RuleTest {

    @Test
    public void should_IdentifyQualifyingItemsB_WithQtyMultipleOf2() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        Map<Item, Integer> map = Map.of(itemA, 1, itemB, 2, itemC, 3);

        Map<Item, Integer> expected = Map.of( itemB, 2 );
        Map<Item, Integer> actual = Rule.filterByBiPredicate(map, Rule.isItemBAndHasQtyMultipleOf2);
        assertEquals(expected, actual);
    }

    @Test
    public void should_IdentifyQualifyingItemsC_WithQtyMultipleOf3() {
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        Map<Item, Integer> map = Map.of(itemA, 1, itemB, 2, itemC, 3);

        Map<Item, Integer> expected = Map.of( itemC, 3 );
        Map<Item, Integer> actual = Rule.filterByBiPredicate(map, Rule.isItemCAndHasQtyMultipleOf3);
        assertEquals(expected, actual);
    }
}
