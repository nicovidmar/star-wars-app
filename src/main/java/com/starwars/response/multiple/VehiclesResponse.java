package com.starwars.response.multiple;

import java.util.List;

import com.starwars.entity.Vehicle;

public class VehiclesResponse {
    private String message;
    private List<VehicleResult> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<VehicleResult> getResult() {
        return result;
    }

    public void setResult(List<VehicleResult> result) {
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
