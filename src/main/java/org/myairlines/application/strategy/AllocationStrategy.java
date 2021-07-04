package org.myairlines.application.strategy;

import org.myairlines.application.model.Position;
import org.myairlines.application.model.Seat;

public interface AllocationStrategy {
	Seat getNextAvailbleSeat();

	void allocateSeat(Position position, int passengerId);

	Seat[][] getSeats();
}
