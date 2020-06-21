package com.bipul.groceryshope.interfaces;

import com.bipul.groceryshope.modelForProducts.ProductList;

public interface OnCartListener {
    void OnCartAdded(ProductList productList);
    void onCartRemoved(ProductList productList);
    void onDeleteFromCart(ProductList productList);

}
