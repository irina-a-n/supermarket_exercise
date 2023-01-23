package com.supermarket.checkout;

import com.supermarket.checkout.model.Item;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Rule {
    // Pricing rules could be composed from predicates:
    private static Predicate<Integer> isDivBy2  = x -> x % 2 == 0;
    private static Predicate<Integer> isDivBy3 = x -> x % 3 == 0;
    private static Predicate<Item> isItemB  = i -> i.getSku().equals("B");
    private static Predicate<Item> isItemC  = i -> i.getSku().equals("C");
    static BiPredicate<Item, Integer> isItemBAndHasQtyMultipleOf2 = (item, qty) -> isDivBy2.test(qty) && isItemB.test(item);

    static BiPredicate<Item, Integer> isItemCAndHasQtyMultipleOf3 = (item, qty) -> Integer.valueOf(3).equals(qty) && isItemC.test(item);

    public static <K, V> Map<K, V> filterByBiPredicate(Map<K, V> map, BiPredicate<K, V> biPredicate) {
        return map.entrySet()
                .stream()
                .filter(x -> biPredicate.test(x.getKey(), x.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }



}
