package com.example.fairpaysystem.service;

import com.example.fairpaysystem.model.BaseConfig;
import com.example.fairpaysystem.model.Employee;
import com.example.fairpaysystem.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class EmployeeUtility {

    private final Random rand = new Random();
    public Employee whoPays(BaseConfig baseConfig) {
        HashMap<String, Employee> employeeList = baseConfig.getEmployees();
        HashMap<String, Product> productList = baseConfig.getProducts();
        double total = 0.0;

        if (employeeList == null || employeeList.isEmpty()) {
            return null;
        }

        // Find the minimum paid amount among all employees
        double minPaid = employeeList.values().stream().mapToDouble(Employee::getTotalPaid).min().orElse(Double.MAX_VALUE);

        // Get all employees who have paid the minimum amount
        List<Employee> shouldPay = new ArrayList<>();
        for (Employee employee : employeeList.values()) {
            total += productList.get(employee.getFavoriteDrinkId()).getDrinkPrice();
            if (employee.getTotalPaid() == minPaid) {
                shouldPay.add(employee);
            }
        }

        Employee mustPay = shouldPay.isEmpty() ? null : shouldPay.get(rand.nextInt(shouldPay.size()));
        if(mustPay == null) {
            return null;
        }

        baseConfig.getEmployees().get(mustPay.getId()).setTotalPaid(baseConfig.getEmployees().get(mustPay.getId()).getTotalPaid() + total);

        // Randomly select employee from the shouldPay list
        return mustPay;
    }


    public void addEmployee(BaseConfig baseConfig, Employee employee) {

        baseConfig.getEmployees().put(employee.getId(), employee);

    }

    public void removeEmployee(BaseConfig baseConfig, Employee employee) {

        baseConfig.getEmployees().remove(employee.getId());

    }

    public void changeFavoriteDrink(BaseConfig baseConfig, Product product, Employee employee) {

        if (baseConfig.getProducts().get(employee.getFavoriteDrinkId()).equals(product)) {
            throw new IllegalArgumentException("Favorite drink of " + employee.getName() + " is already set to " + product.getName());
        }

        baseConfig.getEmployees().get(employee.getId()).setFavoriteDrinkId(product.getId());

    }


}
