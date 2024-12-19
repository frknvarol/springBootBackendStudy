package com.dreamgames.backendengineeringcasestudy;

import com.dreamgames.backendengineeringcasestudy.model.User;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@SpringBootApplication
public class BackendEngineeringCaseStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEngineeringCaseStudyApplication.class, args);

        System.out.println("**********************************************************************");
        System.out.println("************* Server is started. Listening port 8080 ... *************");
        System.out.println("**********************************************************************");
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a few users
            //repository.save(new User("frkn", 'A'));
            //repository.save(new User("ahmet21", 'B'));
            //repository.save(new User("tuana48", 'A'));
            //repository.save(new User("fatih619", 'A'));
            //repository.save(new User("GrImEs", 'B'));

            // fetch all users
            System.out.println("Users found with findAll():");
            System.out.println("-------------------------------");
            repository.findAll().forEach(User -> {
                System.out.println(User.toString());
            });
            System.out.println("");


            // fetch customers by username
            System.out.println("Customer found with findByLastName('frkn'):");
            System.out.println("--------------------------------------------");
            repository.findByUsername("frkn").forEach(frkn -> {
                System.out.println(frkn.toString());
            });
            System.out.println("");
        };
    }
}
