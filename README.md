Assumptions:
1. Prices are in pence (therefore whole numbers) and within the range of int
2. Product price has to be >= 0
3. One item in the basket can only be included in one promotion. Once it's been included in one promotion it's excluded from other promotions. 
The order in which promotions are applied is fixed here (N items for Y price, then M items for the price of M-1, then meal deal) but in a more 
realistic scenario it could be chosen such as to minimise/maximise total price.
4. There are only 2 items included in the meal deal but this could be extended to a list of items