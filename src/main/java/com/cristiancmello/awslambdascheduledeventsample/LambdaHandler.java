package com.cristiancmello.awslambdascheduledeventsample;

import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class LambdaHandler implements Consumer<ScheduledEvent> {
    private final PetRepository petRepository;

    public LambdaHandler(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void accept(ScheduledEvent scheduledEvent) {
        log.info(scheduledEvent.getRegion());
        log.info(scheduledEvent.getSource());
        log.info(scheduledEvent.getTime().toString());

        petRepository.save(Pet.builder().build());
    }
}
