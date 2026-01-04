class Car {
    private String carId;
    private String model;
    private String licensePlate;
    private CarStatus status;
    private CarType type;

    public Car(String carId, String model, String licensePlate) {
        this.carId = carId;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }
    public CarStatus getStatus() {
        return this.status;
    }
}
enum CarType {
    SEDAN,
    SUV,
    HATCHBACK,
    CONVERTIBLE
}

enum CarStatus {
    AVAILABLE,
    RENTED,
    MAINTENANCE
}