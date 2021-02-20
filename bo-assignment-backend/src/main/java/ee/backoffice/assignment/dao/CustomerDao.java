package ee.backoffice.assignment.dao;

import ee.backoffice.assignment.domain.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
@Component
public class CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> findAllCustomers() {
        String sql =
                "SELECT id, personal_id, first_name, last_name, birth_date, balance, status FROM customer WHERE 1=1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Customer.class));
    }

    public List<Customer> findCustomers(String firstName) {
        String sql =
                "SELECT id, personal_id, first_name, last_name, birth_date, balance, status FROM customer WHERE first_name = ?";
        return jdbcTemplate.query(sql, new Object[]{firstName}, new BeanPropertyRowMapper(Customer.class));
    }

    public Customer findCustomer(Long customerId) {
        String sql = "SELECT id, personal_id, first_name, last_name, birth_date, balance, status FROM customer WHERE id = ?";
        return (Customer) jdbcTemplate.queryForObject(sql, new Object[]{customerId},
                new BeanPropertyRowMapper(Customer.class));
    }

    public void setStatus(Long customerId, String status) {
        String sql = "UPDATE customer SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, customerId);
    }

    public void setRecieverAccount(Long customerId, Integer balance) {
        String sql = "UPDATE customer SET balance = balance + ? WHERE id = ?";
        jdbcTemplate.update(sql, balance, customerId);
    }

    public void setSenderAccount(Long customerId, Integer balance) {
        String sql = "UPDATE customer SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(sql, balance, customerId);
    }
}