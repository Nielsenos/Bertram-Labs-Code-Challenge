package com.example.fairpaysystem.model;

public class Employee {

    private String id;
    private String name;
    private String favoriteDrinkId;
    private Double totalPaid;

    //    Constructor for testing purposes
    public Employee(String employeeId, String name, String favoriteDrinkId, Double totalPayed) {
        this.id = employeeId;
        this.name = name;
        this.favoriteDrinkId = favoriteDrinkId;
        this.totalPaid = totalPayed;
    }

    //    Default Ctor needed for run success since other ctor exists for testing
    private Employee() {
    }

    public String getId()
    {
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

    public String getFavoriteDrinkId() {
        return favoriteDrinkId;
    }

    public void setFavoriteDrinkId(String favoriteDrinkId) {
        this.favoriteDrinkId = favoriteDrinkId;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }
}
