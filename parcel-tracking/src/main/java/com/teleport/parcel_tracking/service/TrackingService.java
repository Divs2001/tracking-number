package com.teleport.parcel_tracking.service;

import com.teleport.parcel_tracking.dto.TrackingNumberResponse;
import reactor.core.publisher.Mono;

public interface TrackingService {

    public Mono<TrackingNumberResponse> generateTrackingNumberResponse(String originCountryId, String destinationCountryId);
}
