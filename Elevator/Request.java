class Request {
    - floor: int
    - type: RequestType // PICKUP_UP, PICKUP_DOWN, DESTINATION

    + Request(floor, type)
    + getFloor() -> int
    + getType() -> RequestType
}


enum RequestType {
    PICKUP_UP
    PICKUP_DOWN
    DESTINATION
}