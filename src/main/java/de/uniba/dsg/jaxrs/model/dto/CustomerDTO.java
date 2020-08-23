package de.uniba.dsg.jaxrs.model.dto;


import javax.xml.bind.annotation.*;


/*
 * this class is used to send back Customer Objects to client
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer")
@XmlType(propOrder = {"firstName", "lastName", "userName"})
public class CustomerDTO {

    @XmlElement(required = true)
    private String firstName;

    @XmlElement(required = true)
    private String lastName;

    @XmlElement(required = true)
    private String userName;

    public CustomerDTO() {
    }
    public CustomerDTO(String firstName, String lastName, String userName) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

}
