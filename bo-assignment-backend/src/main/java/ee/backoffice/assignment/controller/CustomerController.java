package ee.backoffice.assignment.controller;

import ee.backoffice.assignment.dao.CustomerDao;
import ee.backoffice.assignment.domain.customer.Customer;
import ee.backoffice.assignment.execution.FindCustomerDetailsExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ee.backoffice.assignment.domain.customer.CustomerStatus.ACTIVE;
import static ee.backoffice.assignment.domain.customer.CustomerStatus.BLOCKED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
@RestController
@RequestMapping(method = {GET, POST})
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private FindCustomerDetailsExecution findCustomerDetailsExecution;

    @GetMapping("customers/findAll")
    public List<Customer> findAllCustomers() {
        return customerDao.findAllCustomers();
    }

    @GetMapping("customers/find")
    public List<Customer> findCustomers(@RequestParam String firstName) {
        return customerDao.findCustomers(firstName);
    }

    @GetMapping("customer/{customerId}/find")
    public Customer find(@PathVariable Long customerId) {
        return findCustomerDetailsExecution.find(customerId);
    }

    @PostMapping("customer/{customerId}/activate")
    public Customer activate(@PathVariable Long customerId) {
        customerDao.setStatus(customerId, ACTIVE.toString());
       return findCustomerDetailsExecution.find(customerId);
    }

    @PostMapping("customer/{customerId}/block")
    public Customer close(@PathVariable Long customerId) {
        customerDao.setStatus(customerId, BLOCKED.toString());
        return findCustomerDetailsExecution.find(customerId);
    }
}
