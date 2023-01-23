package com.supermarket.checkout;

import java.util.Map;

import com.supermarket.checkout.model.Item;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CheckoutServiceTest {
    @Test
    public void should_AddProductsToBasket() {
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A",50);
        Item itemB = new Item("A",50);

        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        assertEquals(Map.of(itemA, 1, itemB, 1), checkoutService.getItemsInBasket());
    }

    @Test
    public void should_IncreaseQuantityEachTimeItemIsScanned() {
        CheckoutService checkoutService = new CheckoutService();
        Item item = new Item("A",50);
        checkoutService.scanItem(item);
        checkoutService.scanItem(item);
        assertEquals(Map.of(item, 2), checkoutService.getItemsInBasket());
    }

    @Test
    public void should_ThrowException_When_ItemPriceIsNegative() {
        CheckoutService checkoutService = new CheckoutService();
        Item item = new Item("Z", -10);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.scanItem(item);
        });
        String expectedMessage = "Price of item cannot be < 0";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void should_ReturnQuantityAsZero_When_ItemRetrievedIsNotInBasket() {
        CheckoutService checkoutService = new CheckoutService();
        Item item = new Item("A", 50);
        int actualQuantity = checkoutService.getItemQuantity(item);
        int expectedQuantity = 0;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void should_computeFinalPriceOfBasket() {
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        int actualTotalPrice = checkoutService.computeFinalPrice();
        int expectedTotalPrice = 175;
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }
}
