package ee.backoffice.assignment.execution;

import ee.backoffice.assignment.dao.TransactionDao;
import ee.backoffice.assignment.domain.transaction.Transaction;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Chloe
 * @since 16.02.2021
 */
@Component
public class FindTransactionDetailsExecution {
private static final Logger log = getLogger(FindTransactionDetailsExecution.class);

    @Autowired
    private TransactionDao transactionDao;

    public Transaction find(Long transactionId) {
        log.info("Starting to find transaction-{} details", transactionId);
        return transactionDao.findTransaction(transactionId);
    }
}
