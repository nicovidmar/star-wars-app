package com.starwars.response.single;

import com.starwars.entity.Vehicle;

public class VehicleResponse {
    private String message;
    private VehicleResult result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VehicleResult getResult() {
        return result;
    }

    public void setResult(VehicleResult result) {
        this.result = result;
    }

    public static class VehicleResult {
        private Vehicle properties;

        public Vehicle getProperties() {
            return properties;
        }

        public void setProperties(Vehicle properties) {
            this.properties = properties;
        }
    }
}
