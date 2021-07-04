package org.myairlines.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.myairlines.application.datastore.BookingStore;
import org.myairlines.application.exception.FlightFullException;
import org.myairlines.application.exception.NoPassengersException;
import org.myairlines.application.strategy.AllocationStrategy;
import org.myairlines.application.strategy.AisleWindowMiddleAllocationStrategy;

public class BookingServiceTest {

	@Test
	public void noPassengersException() {
		int[][] input = new int[][] {
			{3,2}, {4,3}, {2,3}, {3,4}
		};
		int passengers = 0;
		AllocationStrategy strategy = new AisleWindowMiddleAllocationStrategy(input);
		BookingStore store = new BookingStore();
		BookingService service = new BookingService(strategy, store);
		assertThrows(NoPassengersException.class, () -> service.bookSeat(passengers));
	}
	
	@Test
	public void successfulBooking() {
		int[][] input = new int[][] {
			{3,2}, {4,3}, {2,3}, {3,4}
		};
		int passengers = 30;
		AllocationStrategy strategy = new AisleWindowMiddleAllocationStrategy(input);
		BookingStore store = new BookingStore();
		BookingService service = new BookingService(strategy, store);
		assertTrue(service.bookSeat(passengers));
	}
	
	@Test
	public void flightFullException() {
		int[][] input = new int[][] {
			{3,2}, {4,3}, {2,3}, {3,4}
		};
		int passengers = 100;
		AllocationStrategy strategy = new AisleWindowMiddleAllocationStrategy(input);
		BookingStore store = new BookingStore();
		BookingService service = new BookingService(strategy, store);
		assertThrows(FlightFullException.class, () -> service.bookSeat(passengers));
	}
}
