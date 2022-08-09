package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private static final String ERROR_MESSAGES_FILE = "/error_messages";
    private static final String ENCODING = "UTF-8";

    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.addBasenames(ERROR_MESSAGES_FILE);
        messages.setDefaultEncoding(ENCODING);
        return messages;
    }

    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .modules(new JavaTimeModule())
                .build()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }*/
}
