package com.bipul.groceryshope.interfaces;

import com.bipul.groceryshope.modelForLatestProduct.Datum;
import com.bipul.groceryshope.modelForProductDetails.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;

public interface OnCartListener {
    void OnCartAdded(ProductList productList);
    void onCartRemoved(ProductList productList);
    void onDeleteFromCart(ProductList productList);

    void OnCartAddedForLatest(Datum productList);
    void onCartRemovedForLatest(Datum productList);

}
