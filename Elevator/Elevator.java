Requirement	What Elevator must track
"Elevators serving 10 floors (0-9)"	Current floor position
"Continue in current direction servicing all requests"	Current direction of travel
"Once inside, users can select one or more destination floors"	Collection of floors to stop at
"System manages 3 elevators"	No extra state needed beyond maintaining multiple instances


class Elevator {
    -currentFloor: int
    -direction: Direction // UP, DOWN, IDLE
    -requests: Set<Request>

    + Elevator()
    + addRequest(int floor, RequestType type): void
    + step(): void
    + getCurrentFloor(): int
    + getDirection(): Direction

    public Elevator() {
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.requests = new HashSet<>();
    }

    public void step() {
        // Case 1: Nothing to do
        if (requests.isEmpty()) {
            direction = Direction.IDLE;
            return;
        }

        // Case 2: If idle, pick direction based on nearest request
        if (direction == Direction.IDLE) {
            Request nearest = null;
            int minDistance = Integer.MAX_VALUE;

            for (Request req : requests) {
                int distance = Math.abs(req.getFloor() - currentFloor);
                if (distance < minDistance ||
                (distance == minDistance &&
                    (nearest == null || req.getFloor() < nearest.getFloor()))) {
                    minDistance = distance;
                    nearest = req;
                }
            }

            direction = (nearest.getFloor() > currentFloor)
                    ? Direction.UP
                    : Direction.DOWN;
        }

        // Case 3: Stop at current floor if needed
        Request pickupRequest = new Request(
                currentFloor,
                direction == Direction.UP ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN
        );
        Request destinationRequest =
                new Request(currentFloor, RequestType.DESTINATION);

        if (requests.contains(pickupRequest) || requests.contains(destinationRequest)) {
            requests.remove(pickupRequest);
            requests.remove(destinationRequest);

            if (requests.isEmpty()) {
                direction = Direction.IDLE;
                return;
            }

            if (!hasStopsInDirection(direction)) {
                direction = (direction == Direction.UP)
                        ? Direction.DOWN
                        : Direction.UP;
            }
            return; // stopped this tick
        }

        // Case 4: Reverse if no requests in current direction
        if (!hasStopsInDirection(direction)) {
            direction = (direction == Direction.UP)
                    ? Direction.DOWN
                    : Direction.UP;
        }

        // Case 5: Move one floor
        if (direction == Direction.UP) {
            currentFloor++;
        } else if (direction == Direction.DOWN) {
            currentFloor--;
        }
    }
    public void addRequest(int floor, RequestType type) {
        requests.add(new Request(floor, type));
    }
    public boolean hasStopsInDirection(Direction dir) {
        for (Request req : requests) {
            if (dir == Direction.UP && req.getFloor() > currentFloor) {
                return true;
            } else if (dir == Direction.DOWN && req.getFloor() < currentFloor) {
                return true;
            }
        }
        return false;
    } 
}