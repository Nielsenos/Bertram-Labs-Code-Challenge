package com.example.fairpaysystem.service;

import com.example.fairpaysystem.model.BaseConfig;
import com.example.fairpaysystem.model.Employee;
import com.example.fairpaysystem.model.Product;

import java.util.ArrayList;

public class ProductUtility {

    public void addNewProduct(BaseConfig baseConfig, Product product) {

        baseConfig.getProducts().put(product.getId(), product);

    }

    public void removeProduct(BaseConfig baseConfig, Product product) {

        baseConfig.getProducts().remove(product.getId());
    }


}
