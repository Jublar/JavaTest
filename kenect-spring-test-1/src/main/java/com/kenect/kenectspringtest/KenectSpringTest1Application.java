package com.kenect.kenectspringtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>KenectSpringTest1Application class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSwagger2
public class KenectSpringTest1Application {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(String[] args) {
		SpringApplication.run(KenectSpringTest1Application.class, args);
    }


}
