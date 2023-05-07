package com.cristiancmello.awslambdascheduledeventsample;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LambdaHandlerTests {
    @MockBean
    PetRepository petRepository;

    @Autowired
    LambdaHandler lambdaHandler;

    @Test
    void shouldSaveAPetWhenFiring() {
        var scheduledEvent = new ScheduledEvent();

        scheduledEvent.setTime(DateTime.now());
        scheduledEvent.setRegion("us-east-1");
        scheduledEvent.setSource("aws.events");

        Mockito.doNothing().when(petRepository).save(Mockito.any());

        lambdaHandler.accept(scheduledEvent);

        Mockito.verify(petRepository, Mockito.times(1)).save(Mockito.any());
    }
}
