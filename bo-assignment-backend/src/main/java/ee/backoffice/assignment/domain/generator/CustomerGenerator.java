package ee.backoffice.assignment.domain.generator;

import com.github.javafaker.Faker;
import ee.backoffice.assignment.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.Map;

import static ee.backoffice.assignment.domain.customer.CustomerStatus.ACTIVE;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
@Component
public class CustomerGenerator {
    private static final Logger log = LoggerFactory.getLogger(CustomerGenerator.class);
    private final Faker faker = new Faker();
    Random rand = new Random(); 

    public List<Customer> generate() {
        log.info("Starting to generate customers");
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            customers.add(createCustomer(i));
        }
        log.info("Customers successfully generated");
        return customers;
    }

    private Customer createCustomer(long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setPersonalId(randomNumeric(10));
        customer.setFirstName(faker.name().firstName());
        customer.setLastName(faker.name().lastName());
        customer.setDateOfBirth(faker.date().birthday());
        customer.setBalance(rand.nextInt(10000));
        customer.setStatus(ACTIVE);
        return customer;
    }
}
