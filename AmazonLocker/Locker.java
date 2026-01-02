class Locker {
    - Compartment[] compartments
    - Map<String, AccessToken> accessTokenMap
    - Set<Compartment> occupiedCompartments

    + depositPackage(String packageSize): {CompartmemtId, tokenCode} | error
    + pickUpPackage(String accessTokenCode): String  | error

    public depositPackage(String packageSize): {CompartmemtId, tokenCode} | error {
        // Find an available compartment of the requested size
        int compartmentId = Compartment.getAvailableCompartmentId(packageSize);
        if(compartmentId == -1) {
            return error("No available compartment of the requested size")
        }

        // Generate access token
        AccessToken accessToken = generateAccessToken(compartmentId);

        // update occupied compartments and access token map
        occupiedCompartments.add(compartmentId);
        accessTokenMap.put(accessToken.getTokenCode(), accessToken);
        return {compartmentId, accessToken.getTokenCode()};
    }

    public pickUpPackage(String accessTokenCode): String  | error {
        // Lookup access token
        AccessToken accessToken = accessTokenMap.get(accessTokenCode);
        if(accessToken == null) {
            return error("Invalid access token")
        }

        // Validate access token
        Compartment compartment = accessToken.getCompartmentIfValid();
        if(compartment is error) {
            return error("Access token expired")
        }

        // Free up compartment and remove access token
        occupiedCompartments.remove(compartment.getId());
        accessTokenMap.remove(accessTokenCode);
        return "Package picked up from compartment " + compartment.getId();
    }

    public String getAvailableCompartmentId(String size): int {
        for(Compartment compartment : compartments) {
            if(compartment.getSize() == size && !occupiedCompartments.contains(compartment.getId())) {
                return compartment.getId();
            }
        }
        return -1;
    }

    public AccessToken generateAccessToken(int compartmentId): AccessToken {
        String tokenCode = UUID.randomUUID().toString();
        LocalDateTime expirationTimestamp = LocalDateTime.now().plusDays(7);
        Compartment compartment = compartments[compartmentId];
        return new AccessToken(tokenCode, expirationTimestamp, compartment);
    }

    public String generateUniqueTokenCode(): String {
        // Implementation to generate a unique token code
        return UUID.randomUUID().toString();
    }

    public void clearDeliveredPackages(String accessTokenCode) {
        LocalDateTime currentTime = LocalDateTime.now();
        AccessToken accessToken = accessTokenMap.get(accessTokenCode);

        if(accessToken != null && currentTime > accessToken.expirationTimestamp) {
            Compartment compartment = accessToken.getCompartment();
            occupiedCompartments.remove(compartment.getId());
            accessTokenMap.remove(accessTokenCode);
        }
      
    }
}