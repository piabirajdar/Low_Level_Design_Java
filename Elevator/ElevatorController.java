Requirement	What ElevatorController must track
"System manages 3 elevators serving 10 floors"	The collection of elevators it controls
"Users can request an elevator from any floor"	A way to receive and process hall calls
"Discrete time steps"	A method to advance all elevators


class ElevatorController {
    - elevators: List<Elevator>

    + ElevatorController(elevators: List<Elevator>)
    + requestElevator(floor: int, direction: Direction): void
    + step(): void


    public ElevatorController(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void requestElevator(int floor, Direction direction) {
        // Logic to assign the request to the best-suited elevator
        // This is a placeholder for the actual implementation
        if (floor < 0 || floor > 9) {
            throw new IllegalArgumentException("Floor must be between 0 and 9");
        }
        if (direction != Direction.UP && direction != Direction.DOWN) {
            throw new IllegalArgumentException("Direction must be UP or DOWN");
        }

        Elevator bestElevatorIndex = selectBestElevator(floor, direction);
        RequestType type = (direction == Direction.UP) ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
        bestElevatorIndex.addRequest(floor, type);
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public Elevator selectBestElevator(int floor, Direction direction) {
        // Priority 1: Elevators already committed to this floor/direction
        Elevator best = findMovingElevatorInRequiredDirection(floor, direction);
        if (best != null) {
            return best;
        }

        // Priority 2: Nearest idle elevator
        best = findNearestIdle(floor);
        if (best != null) {
            return best;
        }

        // Priority 3: Any nearest elevator
        return findNearest(floor);
    }

    private Elevator findMovingElevatorInRequiredDirection(int floor, Direction direction) {
        for (Elevator elevator : elevators) {
            if (elevator.getDirection() == direction) {
                if ((direction == Direction.UP && elevator.getCurrentFloor() <= floor) ||
                    (direction == Direction.DOWN && elevator.getCurrentFloor() >= floor)) {
                    return elevator;
                }
            }
        }
        return null;
    }

    private Elevator findNearestIdle(int floor) {
        Elevator best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            if (elevator.getDirection() == Direction.IDLE) {
                int distance = Math.abs(elevator.getCurrentFloor() - floor);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    best = elevator;
                }
            }
        }
        return best;
    }

    private Elevator findNearest(int floor) {
        Elevator best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = elevator;
            }
        }
        return best;
    }
}

enum Direction {
    UP
    DOWN
    IDLE
}