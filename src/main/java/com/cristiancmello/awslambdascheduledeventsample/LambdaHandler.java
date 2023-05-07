package com.cristiancmello.awslambdascheduledeventsample;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LambdaHandler implements Consumer<ScheduledEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void accept(ScheduledEvent scheduledEvent) {
        logger.info(scheduledEvent.getRegion());
        logger.info(scheduledEvent.getSource());
        logger.info(scheduledEvent.getTime().toString());
    }
}
