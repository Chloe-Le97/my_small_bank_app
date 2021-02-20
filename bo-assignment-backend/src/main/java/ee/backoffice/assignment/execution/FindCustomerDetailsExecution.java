package ee.backoffice.assignment.execution;

import ee.backoffice.assignment.dao.CustomerDao;
import ee.backoffice.assignment.domain.customer.Customer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
@Component
public class FindCustomerDetailsExecution {
private static final Logger log = getLogger(FindCustomerDetailsExecution.class);

    @Autowired
    private CustomerDao customerDao;

    public Customer find(Long customerId) {
        log.info("Starting to find customer-{} details", customerId);
        return customerDao.findCustomer(customerId);
    }
}
