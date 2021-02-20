package ee.backoffice.assignment.domain.customer;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.String.format;

import java.sql.Array;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
public class Customer {

    private Long id;
    private String personalId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private CustomerStatus status;
    private Integer balance;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    

    @Override
    public String toString() {
        return format(
                "Customer[id=%d, personalId='%s', firstName='%s', lastName='%s', dateOfBirth='%s', balance='%s', status='%s']",
                id, personalId, firstName, lastName, dateOfBirth, balance, status);
    }
}
