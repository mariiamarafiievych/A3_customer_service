package customer.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    private UUID customerId;
    private String firstName;
    private String lastName;

    public Customer(){

    }

    public Customer(UUID customerId, String firstName, String lastName) {
        //this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
