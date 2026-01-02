Requirement	What ParkingLot must track
"System automatically assigns an available compatible spot"	All parking spots in the lot
"System issues a ticket at entry"	Active tickets to validate on exit
"Calculates fee based on time spent (hourly)"	The hourly rate for pricing

class ParkingLot {
    - private List<ParkingSpot> parkingSpots;
    - private Map<String, Ticket> activeTickets;
    - private double hourlyRate;

    + ParkingLot(List<ParkingSpot> spots, double rate)
    + entry(VehicleType vehicleType): Ticket
    + exit(Ticket ticket): double


    public ParkingLot(List<ParkingSpot> spots, double rate) {
        this.parkingSpots = spots;
        this.activeTickets = new HashMap<>();
        this.hourlyRate = rate;
    }
    public Ticket entry(VehicleType vehicleType) {
        ParkingSpot spot = findAvailableParkingSpot(vehicleType);
        if (spot == null) {
            throw new IllegalArgumentException("No available parking spot for vehicle type: " + vehicleType);
        }

        spot.markOccupied();
        String ticketId = UUID.randomUUID().toString();
        long entryTime = System.currentTimeMillis();

        Ticket ticket = new Ticket(ticketId, vehicleType, spot.getSpotId(), entryTime);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    public double exit(Ticket ticket) {
        Ticket activeTicket = activeTickets.get(ticket.getTicketId());
        if (activeTicket == null) {
            throw new IllegalArgumentException("Invalid ticket ID: " + ticket.getTicketId());
        }

        ParkingSpot spot = findParkingSpotById(activeTicket.getParkingSpotId());
        spot.markFree();

        long exitTime = System.currentTimeMillis();
        long durationMillis = exitTime - activeTicket.getEntryTime();
        double hoursParked = Math.ceil(durationMillis / (1000.0 * 60 * 60));
        double fee = hoursParked * hourlyRate;

        activeTickets.remove(ticket.getTicketId());
        return fee;
    }

    public ParkingSpot findAvailableParkingSpot(VehicleType vehicleType) {
        SpotType requiredSpotType = mapVehicleTypeToSpotType(vehicleType);
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied() && spot.getSpotType() == requiredSpotType) {
                return spot;
            }
        }
        return null;
    }

    private SpotType mapVehicleTypeToSpotType(VehicleType vehicleType) {
        if (vehicleType == VehicleType.MOTORCYCLE) {
            return SpotType.MOTORCYCLE;
        }
        if (vehicleType == VehicleType.CAR) {
            return SpotType.CAR;
        }
        if (vehicleType == VehicleType.LARGE) {
            return SpotType.LARGE;
        }
        throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
    }

    public ParkingSpot findParkingSpotById(String spotId) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotId().equals(spotId)) {
                return spot;
            }
        }
        throw new IllegalArgumentException("Parking spot not found: " + spotId);
    }
}