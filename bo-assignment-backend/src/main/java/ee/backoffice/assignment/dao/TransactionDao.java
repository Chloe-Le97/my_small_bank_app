package ee.backoffice.assignment.dao;

import ee.backoffice.assignment.domain.transaction.Transaction;
import ee.backoffice.assignment.domain.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Date;

import javax.xml.crypto.Data;

/**
 * @author Chloe
 * @since 16.02.2021
 */
@Component
public class TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Transaction> findAllTransactions() {
        String sql =
                "SELECT id, SenderID, RecieverID, amount, dateTransaction from transaction WHERE 1=1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Transaction.class));
    }

    public List<Transaction> findTransactionSender(Long senderID) {
        String sql =
                "SELECT transaction.id, transaction.SenderID, transaction.RecieverID, transaction.amount, transaction.dateTransaction, customer.first_name, customer.last_name FROM transaction JOIN customer ON transaction.RecieverID = customer.id WHERE senderID = ?";
        return jdbcTemplate.query(sql, new Object[]{senderID}, new BeanPropertyRowMapper(Transaction.class));
    }

    public List<Transaction> findTransactionReciever(Long recieverID) {
        String sql =
                "SELECT transaction.id, transaction.SenderID, transaction.RecieverID, transaction.amount, transaction.dateTransaction, customer.first_name, customer.last_name FROM transaction JOIN customer ON transaction.SenderID = customer.id WHERE recieverID = ?";
        return jdbcTemplate.query(sql, new Object[]{recieverID}, new BeanPropertyRowMapper(Transaction.class));
    }

    public Transaction findTransaction(Long transactionId) {
        String sql = "SELECT id, SenderID, RecieverID, amount, dateTransaction FROM transaction WHERE id = ?";
        return (Transaction) jdbcTemplate.queryForObject(sql, new Object[]{transactionId},
                new BeanPropertyRowMapper(Transaction.class));
    }

    public void setTransaction(Long SenderID, Long RecieverID, Integer amount, Date dateTransaction) {
        String sql = "INSERT INTO transaction(SenderID, RecieverID, amount, dateTransaction ) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, SenderID, RecieverID, amount, dateTransaction);
    }
    ;

}