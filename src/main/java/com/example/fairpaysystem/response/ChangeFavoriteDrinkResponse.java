package com.example.fairpaysystem.response;

import com.example.fairpaysystem.model.Employee;
import com.example.fairpaysystem.model.Product;

public class ChangeFavoriteDrinkResponse {

    private Product previousFavorite;
    private Product newFavorite;
    private Employee employee;

    private String statusMessage = "Success!";

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Product getPreviousFavorite() {
        return previousFavorite;
    }

    public void setPreviousFavorite(Product previousFavorite) {
        this.previousFavorite = previousFavorite;
    }

    public Product getNewFavorite() {
        return newFavorite;
    }

    public void setNewFavorite(Product newFavorite) {
        this.newFavorite = newFavorite;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
