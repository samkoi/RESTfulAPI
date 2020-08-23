package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Customer;
import de.uniba.dsg.jaxrs.model.logic.Employee;

import java.util.List;

public class CustomerService {

    public static final CustomerService instance = new CustomerService();

    private final DB database;

    public CustomerService() {
        this.database = new DB();
    }

    public void addCustomer(Customer customer) {
        database.addCustomer(customer);
    }

    public int getAndIncrementCustomerID() {
        return database.getAndIncrementCustomerID();
    }
    public boolean deleteCustomer(int id) {
        return database.deleteCustomer(id);
    }

    public List<Customer> getAllCustomer() {
        return database.getAllCustomer();
    }

    public Customer getCustomer(int id) {
        return database.getCustomer(id);
    }

}
