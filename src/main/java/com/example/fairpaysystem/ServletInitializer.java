package com.example.fairpaysystem;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Rest API Initializer
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configures REST API
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FairPaySystemApplication.class);
    }

}
