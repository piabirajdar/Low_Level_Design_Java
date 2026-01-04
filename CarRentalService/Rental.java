class Rental {
    private String rentalId;
    private Reservation reservation;
    private String carId;
    private String userId;
    private LocalDateTime pickupTime;
    private LocalDateTime dropOffTime;
    private double finalAmount;
    private RentalStatus status;

    public Rental(String rentalId, Reservation reservation, String carId, String userId, LocalDateTime pickupTime) {
        this.rentalId = rentalId;
        this.reservation = reservation;
        this.carId = carId;
        this.userId = userId;
        this.pickupTime = pickupTime;
    }

    public void startRental() {
     if (this.status != RentalStatus.CREATED) {
            throw new IllegalStateException("Rental cannot be started in its current state.");
    }
        this.status = RentalStatus.ACTIVE;
        this.pickupDate = LocalDate.now().toString();
    }

    public void endRental() {
        if (this.status != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Rental cannot be ended in its current state.");
        } else {
           
            this.dropOffTime = LocalDateTime.now().toString();

            this.finalAmount = reservation.calculateTotalAmount(pickupTime, dropOffTime);
            this.status = RentalStatus.COMPLETED;
            reservation.complete();
        }
    }
}

enum RentalStatus {
    CREATED,
    ACTIVE,
    COMPLETED
}