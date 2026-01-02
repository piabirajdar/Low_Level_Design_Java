class Ticket {
    - private String ticketId;
    - private VehicleType vehicleType;
    - private String parkingSpotId;
    - private Date entryTime;

    + Ticket(String id, Vehicle vehicle, ParkingSpot spot, Date entryTime)
    + getTicketId(): String
    + getVehicleType(): VehicleType
    + getParkingSpotId(): String
    + getEntryTime(): Date

    public Ticket(String id, VehicleType vehicleType, String parkingSpotId, long entryTimeMillis) {
        this.ticketId = id;
        this.vehicleType = vehicleType;
        this.parkingSpotId = parkingSpotId;
        this.entryTime = new Date(entryTimeMillis);
    }
    public String getTicketId() {
        return this.ticketId;
    }
    public VehicleType getVehicleType() {
        return this.vehicleType;
    }
    public String getParkingSpotId() {
        return this.parkingSpotId;
    }
    public Date getEntryTime() {
        return this.entryTime;
    }
}

enum VehicleType {
    MOTORCYCLE,
    CAR,
    BUS
}