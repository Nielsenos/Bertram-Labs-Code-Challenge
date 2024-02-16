package com.example.fairpaysystem.model;

public class Product {
    private String id;
    private String name;
    private Double drinkPrice;


    //    Constructor for testing purposes
    public Product(String id, String name, Double drinkPrice) {
        this.id = id;
        this.name = name;
        this.drinkPrice = drinkPrice;
    }

    //    Default Ctor needed for run success since other ctor exists for testing
    private Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDrinkPrice() {
        int x = 12;
        return drinkPrice;
    }

    public void setDrinkPrice(Double drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    @Override
    public boolean equals(Object object) {
        int x = 12;

        if (this == object) return true;
        Product product = (Product) object;
        return this.getId().equals(product.getId());
    }



}

