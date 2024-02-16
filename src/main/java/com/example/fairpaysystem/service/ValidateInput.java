package com.example.fairpaysystem.service;

import com.example.fairpaysystem.model.BaseConfig;
import com.example.fairpaysystem.model.Employee;
import com.example.fairpaysystem.model.Product;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.ArrayList;

public class ValidateInput {

    public void validateRemoveProductInput(BaseConfig baseConfig, String productId) {

        if (productId.matches("\\d+")) {

            if (baseConfig.getProducts().containsKey(productId)) {
                for (Employee employee : baseConfig.getEmployees().values()) {
                    if (employee.getFavoriteDrinkId().equals(baseConfig.getProducts().get(productId).getId())) {
                        throw new IllegalArgumentException("Drink that is trying to be removed is currently a favorite of an employee, please check employee information and change the favorite drinks of the appropriate employees, then product can be removed!");
                    }
                }
            } else {
                throw  new IllegalArgumentException("Product ID does not exist in the system, please check product information!");
            }

        } else {

            throw  new IllegalArgumentException("Invalid product ID, please enter a digit that corresponds to the ID of the product you are trying to remove!");

        }
    }

    public void validateAddProductInput(BaseConfig baseConfig, String productId, String name, String drinkPrice) {

        if (productId.matches("\\d+") && name.matches("[a-zA-Z ]+") && drinkPrice.matches("\\d+\\.\\d{2}")) {

            if (baseConfig.getProducts().containsKey(productId)) {
                throw new IllegalArgumentException("Product ID already exists! Please review available product options and their associated IDs!");

            } else {
                for (Product p : baseConfig.getProducts().values()) {
                    if (p.getName().equals(name)) {
                        throw new IllegalArgumentException("Product already exists! Please review available product options!");
                    }
                }
            }

        } else {

            throw  new IllegalArgumentException("Invalid input for addProduct, please enter a productId: XX, name: ____ ____ and a drink price XX.XX!");

        }
        
    }

    public void validateChangeFavoriteDrinkInput(BaseConfig baseConfig, String employeeId, String productId) {

        if (productId.matches("\\d+") && employeeId.matches("\\d+")) {

            if (!baseConfig.getProducts().containsKey(productId)) {

                throw new IllegalArgumentException("The favorite drink that you are trying to add does not exist, please get the proper ID of the product you are searching for or add the product to the record!");

            } else if (!baseConfig.getEmployees().containsKey(employeeId)) {

                throw new IllegalArgumentException("The given employee ID does not have an associated employee, please get the proper ID of the employee you are trying to change the favorite drink of or add a new employee with the associatee ID");

            }

        } else {

            throw  new IllegalArgumentException("Invalid product ID or employee ID, please enter a valid digit for both!");

        }


    }

    public void validateAddEmployeeInput(BaseConfig baseConfig, String employeeId, String name, String favoriteDrinkId) {

        if (employeeId.matches("\\d+") && name.matches("[a-zA-Z ]+") && favoriteDrinkId.matches("\\d+")) {

            if (baseConfig.getEmployees().containsKey(employeeId)) {
                throw new IllegalArgumentException("Employee ID already exists! Please review employee information!");
            }

            if(!baseConfig.getProducts().containsKey(favoriteDrinkId)) {
                throw new IllegalArgumentException("The favorite drink that you are trying to add does not exist, please get the proper ID of the product you are searching for or add the product to the record!");
            }

        } else {

            throw  new IllegalArgumentException("Invalid input for addEmployee, please enter an employeeId: XX, name: ____ ____ and a favoriteDrinkId XX!");

        }

    }

    public void validateRemoveEmployeeInput(BaseConfig baseConfig, String employeeId) {

        if (employeeId.matches("\\d+")) {

           if (!baseConfig.getEmployees().containsKey(employeeId)) {

               throw new IllegalArgumentException("Employee ID does not exist in the system, please check employee information!");

           }

        } else {

            throw  new IllegalArgumentException("Invalid employee ID, please enter a digit that corresponds to the ID of the employee you are trying to remove!");

        }

    }

}
