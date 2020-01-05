package app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Aggregator implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Aggregator.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new File("static/images/user").mkdirs();
        new File("static/images/varia").mkdirs();
        new File("static/images/item").mkdirs();
    }
}
