// 2️⃣ Why Reservation and Rental must be separate
// Reservation = intent

// Planned dates

// Can be cancelled

// May never turn into an actual rental

// Rental = actual usage

// Starts when car is picked up

// Ends when car is returned

// Knows actual duration & final amount

// If you merge them, you lose clarity and correctness.


// Car A
// - Reservation 1: Jan 1 → Jan 3
// - Reservation 2: Jan 5 → Jan 7

class Reservation {
    private String reservationId;
    private String carId;
    private String userId;
    private String startDate;
    private String endDate;
    private ReservationStatus status;

    public Reservation(String reservationId, String carId, String userId, String startDate, String endDate) {
        this.reservationId = reservationId;
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //  + calculateTotalAmount();
    //  + confirm();
    //  + cancel();

     public double calculateTotalAmount(double dailyRate) {
        // Simple calculation assuming dates are in "YYYY-MM-DD" format
        String[] startParts = this.startDate.split("-");
        String[] endParts = this.endDate.split("-");

        int startDay = Integer.parseInt(startParts[2]);
        int endDay = Integer.parseInt(endParts[2]);

        int rentalDays = endDay - startDay + 1; // inclusive of both start and end date
        return rentalDays * dailyRate;
    }

    public void confirm() {
        if (this.status != ReservationStatus.CREATED) {
            throw new IllegalStateException("Only created reservations can be confirmed.");
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status != ReservationStatus.CREATED && this.status != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Only created or confirmed reservations can be cancelled.");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed reservations can be completed.");
        }
        this.status = ReservationStatus.COMPLETED;
    }

    public boolean overlaps(String otherStart, String otherEnd) {
        return !(this.endDate.compareTo(otherStart) < 0 || this.startDate.compareTo(otherEnd) > 0);
    }
}

enum ReservationStatus {
    CONFIRMED,
    CANCELLED,
    CREATED,
    COMPLETED
}
