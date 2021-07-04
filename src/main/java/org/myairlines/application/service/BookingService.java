package org.myairlines.application.service;

import org.myairlines.application.datastore.BookingStore;
import org.myairlines.application.exception.NoPassengersException;
import org.myairlines.application.model.BookingInformation;
import org.myairlines.application.model.Seat;
import org.myairlines.application.strategy.AllocationStrategy;

public class BookingService {

	private AllocationStrategy strategy;
	private BookingStore bookingStore;

	public BookingService(AllocationStrategy strategy, BookingStore bookingStore) {
		this.strategy = strategy;
		this.bookingStore = bookingStore;
	}
	
	public boolean bookSeat(int passengers) {
		if(passengers == 0) { throw new NoPassengersException("Passengers count is 0"); }
		for(int passenger = 1; passenger <= passengers; passenger ++) {
			Seat seat = strategy.getNextAvailbleSeat();
			strategy.allocateSeat(seat.getPosition(), passenger);
			BookingInformation bookingInformation = new BookingInformation(seat, passenger);
			bookingStore.addBooking(bookingInformation);
		}
		return true;
	}
	
	public void display() {
		Seat[][] seats = strategy.getSeats();
		
		for(int i = 0; i < seats.length; i ++) {
			for(int j = 0; j < seats[i].length; j ++) {
				Seat seat = seats[i][j];
				System.out.print(seat + ", ");
			}
			System.out.println();
		}
	}
}
