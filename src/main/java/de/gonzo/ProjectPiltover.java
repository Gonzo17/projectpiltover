package de.gonzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectPiltover {
    public static void main(String[] args) {
        SpringApplication.run(ProjectPiltover.class);
    }
}