package scesi.org.check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CheckApplication {

    static void main(String[] args) {
        SpringApplication.run(CheckApplication.class, args);
    }

}

