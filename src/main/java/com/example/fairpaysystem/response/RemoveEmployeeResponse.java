package com.example.fairpaysystem.response;

import com.example.fairpaysystem.model.Employee;

import java.util.HashMap;

public class RemoveEmployeeResponse {
    private Employee employeeRemoved;
    private HashMap<String, Employee> employeeList;

    private String statusMessage = "Success!";

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Employee getEmployeeRemoved() {
        return employeeRemoved;
    }

    public void setEmployeeRemoved(Employee employeeRemoved) {
        this.employeeRemoved = employeeRemoved;
    }

    public HashMap<String, Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(HashMap<String, Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
