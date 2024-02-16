package com.example.fairpaysystem.response;

import com.example.fairpaysystem.model.Product;

import java.util.HashMap;

public class RemoveProductResponse {

    private Product removedProduct;
    private HashMap<String, Product> productList;

    private String statusMessage = "Success!";

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Product getRemovedProduct() {
        return removedProduct;
    }

    public void setRemovedProduct(Product removedProduct) {
        this.removedProduct = removedProduct;
    }

    public HashMap<String, Product> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Product> productList) {
        this.productList = productList;
    }
}
