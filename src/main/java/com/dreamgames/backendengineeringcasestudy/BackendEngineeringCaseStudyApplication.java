package com.dreamgames.backendengineeringcasestudy;
import com.dreamgames.backendengineeringcasestudy.model.Event;
import com.dreamgames.backendengineeringcasestudy.model.Invitation;
import com.dreamgames.backendengineeringcasestudy.repository.InvitationRepository;
import com.dreamgames.backendengineeringcasestudy.repository.UserRepository;
import com.dreamgames.backendengineeringcasestudy.service.EventService;
import com.dreamgames.backendengineeringcasestudy.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@SpringBootApplication
public class BackendEngineeringCaseStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEngineeringCaseStudyApplication.class, args);

        System.out.println("**********************************************************************");
        System.out.println("************* Server is started. Listening port 8080 ... *************");
        System.out.println("**********************************************************************");
    }

            /*
    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a few users
            repository.save(new User("frkn", 'A'));
            repository.save(new User("ahmet21", 'B'));
            repository.save(new User("tuana48", 'A'));
            repository.save(new User("fatih619", 'A'));
            repository.save(new User("GrImEs", 'B'));

            // fetch all users
            System.out.println("Users found with findAll():");
            System.out.println("-------------------------------");
            repository.findAll().forEach(User -> {
                System.out.println(User.toString());
            });
            System.out.println("");


            // fetch customers by  username
            System.out.println("Customer found with findByLastName('frkn'):");
            System.out.println("--------------------------------------------");
            repository.findByUsername("frkn").forEach(frkn -> {
                System.out.println(frkn.toString());
            });
            System.out.println("");



        };


    }
            */

    @Bean
    public CommandLineRunner demo(InvitationService invitationService, EventService eventService, UserRepository userRepository) {
        return (args) -> {

            Event currentEvent = eventService.getActiveEvent().orElseThrow(()-> new RuntimeException("no active event"));

            invitationService.invitePartner(1L, 3L, currentEvent);

            System.out.println(currentEvent.getStartTime());
            System.out.println(currentEvent.getEndTime());
            System.out.println(currentEvent.getName());
            System.out.println(eventService.isEventActive(currentEvent));



        };


    }
}
