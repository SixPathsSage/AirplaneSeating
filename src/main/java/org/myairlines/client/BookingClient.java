package org.myairlines.client;

import org.myairlines.application.datastore.BookingStore;
import org.myairlines.application.exception.FlightFullException;
import org.myairlines.application.service.BookingService;
import org.myairlines.application.strategy.AllocationStrategy;
import org.myairlines.application.strategy.AisleWindowMiddleAllocationStrategy;

public class BookingClient {

	public static void main(String[] args) {
		BookingStore store = new BookingStore();
		try {
			int[][] input = new int[][] { { 3, 2 }, { 4, 3 }, { 2, 3 }, { 3, 4 } };
			AllocationStrategy strategy = new AisleWindowMiddleAllocationStrategy(input);
			BookingService service = new BookingService(strategy, store);
			service.bookSeat(30);
			service.display();
		} catch (FlightFullException flightFullException) {
			int lastSuccessfulPassenger = store.getLastBooking().getPassengerId();
			System.out.println(flightFullException.getMessage() + " Tickets booked successfully till passenger "
					+ lastSuccessfulPassenger);
		}
	}
}
