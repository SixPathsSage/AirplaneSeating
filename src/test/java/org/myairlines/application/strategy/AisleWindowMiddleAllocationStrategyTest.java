package org.myairlines.application.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.myairlines.application.exception.FlightFullException;
import org.myairlines.application.exception.InvalidInputException;
import org.myairlines.application.model.SeatType;

public class AisleWindowMiddleAllocationStrategyTest {

	@Test
	public void testGridSize() {
		int[][] input = new int[][] {
			{3,2}, {4,3}, {2,3}, {3,4}
		};
		AllocationStrategy strategy = new AisleWindowMiddleAllocationStrategy(input);
		assertEquals(4, strategy.getSeats().length, "Incorrect Grid Rows Created");
		assertEquals(12, strategy.getSeats()[0].length, "Incorrect Grid Columns Created");
	}
	
	@Test
	public void testSeatAllocation() {
		int[][] input = new int[][] {
			{3,2}, {4,3}, {2,3}, {3,4}
		};
		AllocationStrategy strategy = new AisleWindowMiddleAllocationStrategy(input);
		int calculateAisleSeats = 18;
		for(int count = 0; count < calculateAisleSeats; count ++) {
			SeatType nextAvailableSeatType = strategy.getNextAvailbleSeat().getType();
			if(nextAvailableSeatType != SeatType.AISLE) {
				fail("Got " + nextAvailableSeatType + " instead of AISLE");
			}
		}
		
		int calculatedWindowSeats = 6;
		for(int count = 0; count < calculatedWindowSeats; count ++) {
			SeatType nextAvailableSeatType = strategy.getNextAvailbleSeat().getType();
			if(nextAvailableSeatType != SeatType.WINDOW) {
				fail("Got " + nextAvailableSeatType + " instead of Window");
			}
		}
		
		int calculatedMiddleSeats = 12;
		for(int count = 0; count < calculatedMiddleSeats; count ++) {
			SeatType nextAvailableSeatType = strategy.getNextAvailbleSeat().getType();
			if(nextAvailableSeatType != SeatType.MIDDLE) {
				fail("Got " + nextAvailableSeatType + " instead of Window");
			}
		}
		
		assertThrows(FlightFullException.class, strategy::getNextAvailbleSeat);
	}
	
	@Test
	public void invalidInputException() {
		assertThrows(InvalidInputException.class, () -> new AisleWindowMiddleAllocationStrategy(new int[][] {}));
		assertThrows(InvalidInputException.class, () -> new AisleWindowMiddleAllocationStrategy(new int[][] {{}}));	}
}
