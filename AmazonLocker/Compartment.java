class Compartment {
    - String compartmentId
    - String size

    + Compartment(String compartmentId, String size)
    + getId(): String
    + getSize(): String


    public getId(): String {
        return compartmentId;
    }
    public getSize(): String {
        return size;
    }
}


enum Size {
    SMALL,
    MEDIUM,
    LARGE
}