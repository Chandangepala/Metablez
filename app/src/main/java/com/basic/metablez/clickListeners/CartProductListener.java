package com.basic.metablez.clickListeners;

import com.basic.metablez.models.ProductModel;

public interface CartProductListener {

    void deleteProduct(int position);

    void addProduct(ProductModel productModel, int position);

    void subtractProduct(ProductModel productModel, int position);
}
