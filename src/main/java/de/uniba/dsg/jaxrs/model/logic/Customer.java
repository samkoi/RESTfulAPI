package de.uniba.dsg.jaxrs.model.logic;

import java.util.concurrent.atomic.AtomicInteger;


/*
 * Normal Customer Object
 */
public class Customer {

    private String firstName;
    private String lastName;
    private String userName;
    private int id;
    private static final AtomicInteger count = new AtomicInteger(0);
    public Customer() {

    }

    public Customer(int id, String firstName, String lastName, String userName) {
        this.id = count.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                '}';
    }


    public Customer unmarshall() {
        return new Customer(id, firstName, lastName, userName);
    }
}
