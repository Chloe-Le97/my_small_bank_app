package ee.backoffice.assignment.controller;

import ee.backoffice.assignment.dao.CustomerDao;
import ee.backoffice.assignment.dao.TransactionDao;
import ee.backoffice.assignment.domain.transaction.Transaction;
import ee.backoffice.assignment.domain.customer.Customer;
import ee.backoffice.assignment.execution.FindTransactionDetailsExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.security.Identity;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.logging.Logger;

import static ee.backoffice.assignment.domain.customer.CustomerStatus.ACTIVE;
import static ee.backoffice.assignment.domain.customer.CustomerStatus.BLOCKED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.http.MediaType;

/**
 * @author Sander Kadajane
 * @since 31.01.2019
 */
@RestController
@RequestMapping(method = {GET, POST})
public class TransactionController {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private FindTransactionDetailsExecution findTransactionDetailsExecution;

    @GetMapping("transactions/findAll")
    public List<Transaction> findAllTransactions() {
        return transactionDao.findAllTransactions();
    };

    @PostMapping(
        value = "/transaction",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
        public Customer transaction(@RequestBody Transaction request) {
        transactionDao.setTransaction(request.getSenderID(),request.getRecieverID(),request.getAmount(),request.getDateTransaction());
        customerDao.setRecieverAccount(request.getRecieverID(), request.getAmount());
        customerDao.setSenderAccount(request.getSenderID(), request.getAmount());
        return customerDao.findCustomer(request.getSenderID());
    };

    @GetMapping("transaction/find/sender")
    public List<Transaction> findTransactionSender(@RequestParam Long senderID) {
        return transactionDao.findTransactionSender(senderID);
    };

    @GetMapping("transaction/find/reciever")
    public List<Transaction> findTransactionReciever(@RequestParam Long recieverID) {
        return transactionDao.findTransactionReciever(recieverID);
    };


    @GetMapping("transaction/{id}/find")
    public Transaction find(@PathVariable Long id) {
        return findTransactionDetailsExecution.find(id);
    };

}