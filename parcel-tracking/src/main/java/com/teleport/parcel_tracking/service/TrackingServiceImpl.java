package com.teleport.parcel_tracking.service;

import com.teleport.parcel_tracking.dto.TrackingNumberResponse;
import com.teleport.parcel_tracking.util.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class TrackingServiceImpl implements TrackingService{
    private final SnowflakeIdGenerator generator;

    public TrackingServiceImpl(SnowflakeIdGenerator generator) {
        this.generator = generator;
    }

    Logger logger = LoggerFactory.getLogger(TrackingServiceImpl.class);

    @Override
    public Mono<TrackingNumberResponse> generateTrackingNumberResponse(String originCountryId, String destinationCountryId) {
        logger.info("Inside TrackingServiceImpl class");
        long id = generator.nextId();
        String base36 = Long.toString(id, 36).toUpperCase();
        String tracking = (originCountryId + destinationCountryId + base36);
        return Mono.just(new TrackingNumberResponse(tracking.substring(0, Math.min(16, tracking.length())), Instant.now()));
    }
}
