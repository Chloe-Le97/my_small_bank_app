package ee.backoffice.assignment.controller;

import ee.backoffice.assignment.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Sander Kadajane
 * @since 01.02.2019
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    private static final Logger log = getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public void handleNotFoundException(NotFoundException ex) {
        log.error("Caught NotFoundException exception: {}", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public void handleUnknownException(Exception ex) {
        log.error("Catching unexpected exception", ex);
    }
}
