package com.example.fairpaysystem.response;

import com.example.fairpaysystem.model.Product;

import java.util.HashMap;

public class AddProductResponse {

    private Product productAdded;
    private HashMap<String, Product> productList;
    private String statusMessage = "Success!";

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public HashMap<String, Product> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Product> productList) {
        this.productList = productList;
    }

    public Product getProductAdded() {
        return productAdded;
    }

    public void setProductAdded(Product productAdded) {
        this.productAdded = productAdded;
    }
}
