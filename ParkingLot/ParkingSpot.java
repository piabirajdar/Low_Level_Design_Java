Requirement	What ParkingSpot must track
"System assigns compatible spot"	Spot type (motorcycle, car, large) to match with vehicle type
"Frees the spot for next use"	Whether the spot is currently occupied
"When a vehicle exits, user provides ticket ID"	Unique ID for the spot


class ParkingSpot {
    - private String spotId;
    - private SpotType spotType;
    - private boolean isOccupied;

    + ParkingSpot(String id, SpotType type)
    + isFree(): boolean
    + markOccupied(): void
    + markFree(): void
    + getSpotId(): String
    + getSpotType(): SpotType

    public ParkingSpot(String id, SpotType type) {
        this.spotId = id;
        this.spotType = type;
        this.isOccupied = false;
    }
    public void markOccupied() {
        this.isOccupied = true;
    }
    public void markFree() {
        this.isOccupied = false;
    }
    public boolean isOccupied() {
        return this.isOccupied; 
    }
    public String getSpotId() {
        return this.spotId;
    }
    public SpotType getSpotType() {
        return this.spotType;
    }

}

enum SpotType {
    MOTORCYCLE,
    CAR,
    LARGE
}