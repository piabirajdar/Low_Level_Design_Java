class AccessToken {
    - String tokenCode
    - LocalDateTime expirationTimestamp
    - Compartment compartment

    + AccessToken(String tokenCode, LocalDateTime expirationTimestamp, Compartment compartment)
    + getCompartment(): Compartment
    + getCompartmentIfValid(): Compartment | error
    + getCode(): String

    public getCompartmentIfValid(): Compartment | error {
        LocalDateTime currentTime = LocalDateTime.now();
        if(currentTime > expirationTimestamp) {
            return error("Access token expired");
        }
        return compartment;
    }

    public getCode(): String {
        return tokenCode;
    }


}
