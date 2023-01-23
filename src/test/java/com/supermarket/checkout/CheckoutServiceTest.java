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
        Item itemB = new Item("B",75);

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
        checkoutService.scanItem(item);
        assertEquals(Map.of(item, 3), checkoutService.getItemsInBasket());
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
    public void should_computeFinalPriceOfBasket_When_NoPromotionsApplied() {
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

    @Test
    public void should_computeFinalPriceOfBasket_When_NItemBForY_AppliedOnly() {
        // ItemB, N = 2, Y = 125
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        int actualTotalPrice = checkoutService.computeFinalPrice();
        int expectedTotalPrice = 50 + 125 + 75  ;
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }

    @Test
    public void should_computeFinalPriceOfBasket_When_BuyNGet1Free_AppliedOnly() {
        // ItemC, N = 3
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        int actualTotalPrice = checkoutService.computeFinalPrice();
        int expectedTotalPrice = 50 + 75 + 50;
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }

    @Test
    public void should_computeFinalPriceOfBasket_When_Both_NItemBForY_And_BuyNItemCGet1Free_Applied() {
        // ItemB, N = 2, Y = 125
        // ItemC, N = 3
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        int actualTotalPrice = checkoutService.computeFinalPrice();
        int expectedTotalPrice = 50 + 125 + 50;
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }

    @Test
    public void should_computeFinalPriceOfBasket_When_Both_NItemBForY_And_BuyNItemCGet1Free_Applied_2() {
        // ItemB, N = 2, Y = 125
        // ItemC, N = 3
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        int actualTotalPrice = checkoutService.computeFinalPrice();
        int expectedTotalPrice = 50 + 125 + 75 + 50;
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }

    @Test
    public void should_computeFinalPriceOfBasket_When_AllPromotions_Applied_() {
        // MEAL_DEAL_PRICE = 300, ItemD, ItemE
        CheckoutService checkoutService = new CheckoutService();
        Item itemA = new Item("A", 50);
        Item itemB = new Item("B", 75);
        Item itemC = new Item("C", 25);
        Item itemD = new Item("D", 150);
        Item itemE = new Item("E", 200);
        checkoutService.scanItem(itemA);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemB);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemC);
        checkoutService.scanItem(itemD);
        checkoutService.scanItem(itemE);
        checkoutService.scanItem(itemD);
        checkoutService.scanItem(itemE);
        checkoutService.scanItem(itemD);
        int actualTotalPrice = checkoutService.computeFinalPrice();
        int expectedTotalPrice = 50 + 125 + 75 + 50 + 2*300 + 150;
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }
}
