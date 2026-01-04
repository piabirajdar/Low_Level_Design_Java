class RentalService {
    Map<String, Car> cars;
    Map<String, Car> rentedCars;
    Map<String, Rental> activeRentals;
    Map<String, Reservation> reservations;

    public RentalService() {
        this.availableCars = new HashMap<>();
        this.activeRentals = new HashMap<>();
        this.reservations = new HashMap<>();
    }

    + searchCars();
    + reserveCar();
    + pickUpCar();
    + returnCar();

    public List<Car> searchCars(
            String location,
            CarType carType,
            LocalDateTime start,
            LocalDateTime end
    ) {
        List<Car> result = new ArrayList<>();

        for (Car car : cars.values()) {
            if (!car.getLocation().equals(location)) continue;
            if (car.getCarType() != carType) continue;
            if (car.getStatus() == CarStatus.MAINTENANCE) continue;

            if (isCarAvailable(car.getCarId(), start, end)) {
                result.add(car);
            }
        }
        return result;
    }

    private boolean isCarAvailable(
            String carId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        List<Reservation> reservations =
                reservationsByCar.getOrDefault(carId, List.of());

        for (Reservation r : reservations) {
            if (r.getStatus() != ReservationStatus.CANCELLED &&
                r.overlaps(start, end)) {
                return false;
            }
        }
        return true;
    }

    public Reservation reserveCar(
            String userId,
            String carId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        if (!isCarAvailable(carId, start, end)) {
            throw new IllegalStateException("Car not available for given time window");
        }

        Reservation reservation =
                new Reservation(userId, carId, start, end);

        reservation.confirm();

        reservationsByCar
                .computeIfAbsent(carId, k -> new ArrayList<>())
                .add(reservation);


        cars.get(carId).setStatus(CarStatus.RESERVED);
        return reservation;
    }

    public Rental pickUpCar(String reservationId) {
        Reservation reservation = findReservation(reservationId);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Reservation not eligible for pickup");
        }

        Car car = cars.get(reservation.getCarId());
        car.setStatus(CarStatus.RENTED);

        Rental rental = new Rental(reservation, car.getCarId());
        rental.startRental();

        activeRentals.put(rental.getRentalId(), rental);
        return rental;
    }

    public void returnCar(String rentalId) {
        Rental rental = activeRentals.get(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("Invalid rental id");
        }

        rental.endRental();

        Car car = cars.get(rental.getCarId());
        car.setStatus(CarStatus.AVAILABLE);

        activeRentals.remove(rentalId);
    }
}