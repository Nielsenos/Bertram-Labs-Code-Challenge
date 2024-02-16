package com.example.fairpaysystem.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BaseConfig {

    private HashMap<String, Employee> employees;
    private HashMap<String, Product> products;

    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(HashMap<String, Employee> employees) {
        this.employees = employees;
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }
}
