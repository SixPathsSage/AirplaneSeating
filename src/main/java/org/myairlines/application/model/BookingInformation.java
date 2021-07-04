package org.myairlines.application.model;

import java.util.UUID;

public class BookingInformation {
	private UUID bookingId;
	private int passengerId;
	private Seat seat;
	
	public BookingInformation(Seat seat, int passengerId) {
		super();
		this.bookingId = UUID.randomUUID();
		this.seat = seat;
		this.passengerId = passengerId;
	}
	
	public UUID getBookingId() {
		return bookingId;
	}
	public void setBookingId(UUID bookingId) {
		this.bookingId = bookingId;
	}
	public int getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	
}
