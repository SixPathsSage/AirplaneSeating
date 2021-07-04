package org.myairlines.application.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.myairlines.application.exception.FlightFullException;
import org.myairlines.application.exception.InvalidInputException;
import org.myairlines.application.model.Position;
import org.myairlines.application.model.Seat;
import org.myairlines.application.model.SeatType;

public class AisleWindowMiddleAllocationStrategy implements AllocationStrategy {
	
	private Seat[][] seats;	
	private List<Seat> aisleSeats = new ArrayList<>();
	private List<Seat> windowSeats = new ArrayList<>();
	private List<Seat> middleSeats = new ArrayList<>();
	
	public AisleWindowMiddleAllocationStrategy(int[][] input) {
		if(input == null || input.length == 0 || input[0].length == 0) {
			throw new InvalidInputException("Invalid Seats Data");
		}
		
		int rows = Arrays.stream(input).map(array -> array[1]).max((a, b) -> a > b ? 1 : -1).orElse(0);
		int columns = Arrays.stream(input).mapToInt(array -> array[0]).sum();

		seats = new Seat[rows][columns];
		
		for(int index = 0; index < rows; index ++) {
			Arrays.fill(seats[index], new Seat());
		}
		
		preConfigureAisle(input);
		
		preConfigureWindow();
		
		preConfigureMiddle();
		
		copySeats(input);
	}

	private void copySeats(int[][] input) {
		int rowLimit = 0;
		for(int index = 1; index < seats.length; index ++) {
			int columnLimit = 0;
			for(int[] value: input) {
				rowLimit = value[1];
				columnLimit += value[0];
				if(index < rowLimit) {
					for(int column = columnLimit - value[0]; column < columnLimit; column ++) {
						Seat aboveSeat = seats[index-1][column];
						Seat seat = new Seat(aboveSeat.getType(), new Position(index, column));
						seats[index][seat.getPosition().getColumn()] = seat;
						if(aboveSeat.getType() == SeatType.AISLE) {
							aisleSeats.add(seat);
						} else if(aboveSeat.getType() == SeatType.WINDOW) {
							windowSeats.add(seat);
						} else {
							middleSeats.add(seat);
						}
					}
				}
			}
		}
	}

	private void preConfigureMiddle() {
		for(int index = 0; index < seats[0].length; index ++) {
			SeatType seatType = seats[0][index].getType();
			if(seatType != SeatType.WINDOW && seatType != SeatType.AISLE) {
				Seat seat = new Seat(SeatType.MIDDLE, new Position(0, index));
				seats[0][seat.getPosition().getColumn()] = seat;
				middleSeats.add(seat);
			}
		}
	}
	
	private void preConfigureWindow() {
		Seat first = new Seat(SeatType.WINDOW, new Position(0, 0));
		Seat last = new Seat(SeatType.WINDOW, new Position(0, seats[0].length-1));
		seats[0][first.getPosition().getColumn()] = first;
		seats[0][last.getPosition().getColumn()] = last;
		windowSeats.add(first);
		windowSeats.add(last);
	}

	private void preConfigureAisle(int[][] input) {
		int index = input[0][0]-1;
		int end = seats[0].length - input[input.length-1][0];
		while(index < end) {
			for(int inputIterator = 1; inputIterator < input.length-1; inputIterator ++) {
				for(int counter = 0; counter < 2; counter ++) {
					index += counter;
					Seat seat = new Seat(SeatType.AISLE, new Position(0, index));
					seats[0][seat.getPosition().getColumn()] = seat;
					aisleSeats.add(seat);
				}
				index += input[inputIterator][0] - 1;
				if(index >= end) {
					break;
				}
			}
		}
	}

	@Override
	public Seat getNextAvailbleSeat() {
		if (!aisleSeats.isEmpty()) {
			return aisleSeats.remove(0);
		} else if (!windowSeats.isEmpty()) {
			return windowSeats.remove(0);
		} else if (!middleSeats.isEmpty()) {
			return middleSeats.remove(0);
		} else {
			throw new FlightFullException("Flight was filled completely");
		}
	}

	@Override
	public Seat[][] getSeats() {
		return seats;
	}

	@Override
	public void allocateSeat(Position position, int passengerId) {
		seats[position.getRow()][position.getColumn()].setPassengerId(passengerId);
	}
}
