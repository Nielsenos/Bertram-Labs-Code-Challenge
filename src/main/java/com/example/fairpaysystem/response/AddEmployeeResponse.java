package com.example.fairpaysystem.response;

import com.example.fairpaysystem.model.Employee;

import java.util.HashMap;

public class AddEmployeeResponse {

    private Employee addedEmployee;
    private HashMap<String, Employee> employeeList;

    private String statusMessage = "Success!";

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Employee getAddedEmployee() {
        return addedEmployee;
    }

    public void setAddedEmployee(Employee addedEmployee) {
        this.addedEmployee = addedEmployee;
    }

    public HashMap<String, Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(HashMap<String, Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
