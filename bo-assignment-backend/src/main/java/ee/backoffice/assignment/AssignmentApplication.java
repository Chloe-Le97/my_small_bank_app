package ee.backoffice.assignment;

import ee.backoffice.assignment.dao.JdbcExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AssignmentApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcExecution jdbcExecution;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        createDatabase();
    }

    private void createDatabase() {
        jdbcExecution.createCustomerTable();
        jdbcExecution.insertTestDataToCustomerTable();
        jdbcExecution.createTransactionTable();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}

