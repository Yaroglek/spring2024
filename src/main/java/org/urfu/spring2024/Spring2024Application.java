package org.urfu.spring2024;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SpringBootApplication
@Slf4j
public class Spring2024Application {
    public static void main(String[] args) {
        SpringApplication.run(Spring2024Application.class, args);
    }
}
