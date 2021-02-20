package ee.backoffice.assignment.dao;

import ee.backoffice.assignment.domain.customer.Customer;
import ee.backoffice.assignment.domain.generator.CustomerGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
@Component
public class JdbcExecution {
    private static final Logger log = getLogger(JdbcExecution.class);

    @Autowired
    private CustomerGenerator customerGenerator;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createCustomerTable() {
        log.info("Creating database table for customers");
        jdbcTemplate.execute("DROP TABLE customer IF EXISTS");
        jdbcTemplate.execute(
                "CREATE TABLE customer(id IDENTITY, personal_id VARCHAR(30), first_name VARCHAR(255), " +
                        "last_name VARCHAR(255), birth_date DATE, balance INT(25), status VARCHAR(15))");
    }

    public void insertTestDataToCustomerTable() {
        List<Customer> generatedCustomers = customerGenerator.generate();

        log.info("Starting to insert generated customers to database");
        String sql =
                "INSERT INTO customer(id, personal_id, first_name, last_name, birth_date, balance, status) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, generatedCustomers.get(i).getId());
                ps.setString(2, generatedCustomers.get(i).getPersonalId());
                ps.setString(3, generatedCustomers.get(i).getFirstName());
                ps.setString(4, generatedCustomers.get(i).getLastName());
                ps.setDate(5, new java.sql.Date(generatedCustomers.get(i).getDateOfBirth().getTime()));
                ps.setInt(6, generatedCustomers.get(i).getBalance());
                ps.setString(7, String.valueOf(generatedCustomers.get(i).getStatus()));
            }

            @Override
            public int getBatchSize() {
                return generatedCustomers.size();
            }
        });
        log.info("Customers successfully inserted");
    }

    public void createTransactionTable() {
        log.info("Creating database table for transaction");
        jdbcTemplate.execute("DROP TABLE transaction IF EXISTS");
        jdbcTemplate.execute(
                "CREATE TABLE transaction(id IDENTITY, PRIMARY KEY(id), SenderID INT(25), RecieverID INT(25), dateTransaction DATE, amount INT(25), " +
                "foreign key (SenderID) references customer(id), foreign key (RecieverID) references customer(id))");
    }

}
