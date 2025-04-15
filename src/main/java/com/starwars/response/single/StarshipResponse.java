package com.starwars.response.single;

import com.starwars.entity.Starship;

public class StarshipResponse {
    private String message;
    private StarshipResult result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StarshipResult getResult() {
        return result;
    }

    public void setResult(StarshipResult result) {
        this.result = result;
    }

    public static class StarshipResult {
        private Starship properties;

        public Starship getProperties() {
            return properties;
        }

        public void setProperties(Starship properties) {
            this.properties = properties;
        }
    }
}
