package com.starwars.response.multiple;

import java.util.List;

import com.starwars.entity.Starship;

public class StarshipsResponse {
    private String message;
    private List<StarshipResults> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<StarshipResults> getResult() {
        return result;
    }

    public void setResult(List<StarshipResults> result) {
        this.result = result;
    }

    public static class StarshipResults {
        private Starship properties;

        public Starship getProperties() {
            return properties;
        }

        public void setProperties(Starship properties) {
            this.properties = properties;
        }
    }

}
