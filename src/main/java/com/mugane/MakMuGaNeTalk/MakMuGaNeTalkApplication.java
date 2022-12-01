package com.mugane.MakMuGaNeTalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MakMuGaNeTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakMuGaNeTalkApplication.class, args);
    }

}
