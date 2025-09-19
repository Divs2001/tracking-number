package com.teleport.parcel_tracking.controller;

import com.teleport.parcel_tracking.dto.TrackingNumberResponse;
import com.teleport.parcel_tracking.service.TrackingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TrackingController {

    @Autowired
    private TrackingServiceImpl trackingService;

    Logger logger = LoggerFactory.getLogger(TrackingController.class);

    @GetMapping("/next-tracking-number")
    public Mono<ResponseEntity<TrackingNumberResponse>> getNextTrackingNumber(
            @RequestParam("origin_country_id") String originCountryId,
            @RequestParam("destination_country_id") String destinationCountryId
    ) {
        logger.info("Generating next tracking number");
        return trackingService.generateTrackingNumberResponse(originCountryId, destinationCountryId)
                .map(ResponseEntity::ok);
    }

}
