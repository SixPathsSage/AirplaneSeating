package org.myairlines.application.model;

public class Seat {
	private SeatType type;
	private Position position;
	private Integer passengerId;
	
	public Seat() {
		this.type = SeatType.NONE;
	}

	public Seat(SeatType type, Position position) {
		super();
		this.type = type;
		this.position = position;
	}

	public Integer getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Integer passengerId) {
		this.passengerId = passengerId;
	}

	public SeatType getType() {
		return type;
	}

	public void setType(SeatType type) {
		this.type = type;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return passengerId + "-" +type;
	}

}
