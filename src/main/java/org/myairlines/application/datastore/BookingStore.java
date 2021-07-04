package org.myairlines.application.datastore;

import java.util.ArrayList;
import java.util.List;

import org.myairlines.application.exception.EmptyBookingException;
import org.myairlines.application.model.BookingInformation;

public class BookingStore {

	private List<BookingInformation> bookingInfo;

	public BookingStore() {
		super();
		this.bookingInfo = new ArrayList<>();
	}
	
	public void addBooking(BookingInformation bookingInformation) {
		bookingInfo.add(bookingInformation);
	}

	public List<BookingInformation> getBookingInfo() {
		return bookingInfo;
	}
	
	public BookingInformation getLastBooking() {
		if(bookingInfo.isEmpty()) { throw new EmptyBookingException(); }
		return bookingInfo.get(bookingInfo.size()-1);
	}
	
	
}
