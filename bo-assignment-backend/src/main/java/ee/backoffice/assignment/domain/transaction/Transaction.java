package ee.backoffice.assignment.domain.transaction;

import java.util.ArrayList;
import java.util.Date;

import ee.backoffice.assignment.domain.customer.Customer;

import static java.lang.String.format;

import java.sql.Array;

/**
 * @author Chloe
 * @since 16.02.2021
 */
public class Transaction {

    private Long id;
    private Long senderID;
    private Long recieverID;
    private Integer amount;
    private String firstName;
    private String lastName;
    private Date dateTransaction;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getSenderID() {
        return senderID;
    }

    public void setSenderID(long senderID) {
        this.senderID = senderID;
    }

    public Long getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(long recieverID) {
        this.recieverID = recieverID;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
    

    @Override
    public String toString() {
        return format(
                "Transaction[id=%d, senderID='%s', recieverID='%s', amount='%s', firstName='%s', lastName='%s', dateTransaction='%s']",
                id, senderID, recieverID, amount, firstName, lastName, dateTransaction);
    }
}
