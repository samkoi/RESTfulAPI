package de.uniba.dsg.jaxrs.model.logic;

import java.util.concurrent.atomic.AtomicInteger;


/*
 * Normal Employee Object
 */
public class Employee {

    private String firstName;
    private String lastName;
    private String userName;
    private int id;
    private static final AtomicInteger count = new AtomicInteger(0);

    public Employee() {

    }

    public Employee(int id, String firstName, String lastName, String userName) {
        this.id = count.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                '}';
    }
    public Employee unmarshall() {
        return new Employee(id, firstName, lastName, userName);
    }
}
