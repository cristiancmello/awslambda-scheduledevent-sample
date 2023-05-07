package com.cristiancmello.awslambdascheduledeventsample;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class FooConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Bean
    public Consumer<ScheduledEvent> process() {
        return value -> {
            logger.info(value.getRegion());
            logger.info(value.getSource());
            logger.info(value.getTime().toString());
        };
    }
}
