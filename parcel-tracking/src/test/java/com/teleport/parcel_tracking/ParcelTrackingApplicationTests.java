package com.teleport.parcel_tracking;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ParcelTrackingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testConcurrency() throws Exception {
		int totalRequests = 5000;
		int concurrency = 500;
		WebClient client = WebClient.create("http://localhost:8080");

		ExecutorService pool = Executors.newFixedThreadPool(concurrency);
		Set<String> ids = ConcurrentHashMap.newKeySet();

		CountDownLatch latch = new CountDownLatch(totalRequests);

		for (int i = 0; i < totalRequests; i++) {
			pool.submit(() -> {
				try {
					String trackingNumber = client.get()
							.uri(uriBuilder -> uriBuilder.path("/next-tracking-number")
									.queryParam("origin_country_id", "MY")
									.queryParam("destination_country_id", "ID")
									.queryParam("weight", "1.234")
									.queryParam("created_at", "2018-11-20T19:29:32+08:00")
									.queryParam("customer_id", "de619854-b59b-425e-9db4-943979e1bd49")
									.queryParam("customer_name", "RedBox Logistics")
									.queryParam("customer_slug", "redbox-logistics")
									.build())
							.retrieve()
							.bodyToMono(JsonNode.class)
							.block()
							.get("tracking_number").asText();

					ids.add(trackingNumber);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		pool.shutdown();

		System.out.println("Generated IDs = " + ids.size());
		assertThat(ids.size()).isEqualTo(totalRequests); // ✅ no duplicates
	}


}
