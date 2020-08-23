package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Customer;
import de.uniba.dsg.jaxrs.model.logic.Employee;

import java.util.List;

public class EmployeeService {

    public static final EmployeeService instance = new EmployeeService();

    private final DB database;

    public EmployeeService() {
        this.database = new DB();
    }

    public void addNewEmployee(Employee employee) {
        database.addNewEmployee(employee);
    }

    public int getAndIncrementEmployeeID() {
        return database.getAndIncrementEmployeeID();
    }

    public boolean deleteEmployee(int id) {
        return database.deleteEmployee(id);
    }

    public List<Employee> getAllEmployees() {
        return database.getAllEmployees();
    }

    public Employee getEmployee(int id) {
        return database.getEmployee(id);
    }
}
