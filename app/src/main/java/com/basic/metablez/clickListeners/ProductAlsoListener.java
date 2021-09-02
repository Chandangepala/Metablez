package com.basic.metablez.clickListeners;

import com.basic.metablez.models.ProductAlsoModel;
import com.basic.metablez.models.ProductModel;

public interface ProductAlsoListener {

    void addProductAlso(ProductModel productModel, int position);

    void subtractProductAlso(ProductModel productModel, int position);
}
