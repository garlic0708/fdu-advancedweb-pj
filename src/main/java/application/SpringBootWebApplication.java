package application;

import application.entity.Course;
import application.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class SpringBootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
}
