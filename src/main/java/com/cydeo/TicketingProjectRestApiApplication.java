package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingProjectRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectRestApiApplication.class, args);
    }
    @Bean
    public ModelMapper mapper(){

        return new ModelMapper();
    }

}
