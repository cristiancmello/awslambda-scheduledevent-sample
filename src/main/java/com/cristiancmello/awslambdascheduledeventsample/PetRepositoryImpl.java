package com.cristiancmello.awslambdascheduledeventsample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PetRepositoryImpl implements PetRepository {
    @Override
    public void save(Pet pet) {
        log.info("Saving a pet in database...");
    }
}
