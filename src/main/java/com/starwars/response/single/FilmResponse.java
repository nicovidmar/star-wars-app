package com.starwars.response.single;

import com.starwars.entity.Film;

public class FilmResponse {
    private String message;
    private FilmResult result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FilmResult getResult() {
        return result;
    }

    public void setResult(FilmResult result) {
        this.result = result;
    }

    public static class FilmResult {
        private Film properties;

        public Film getProperties() {
            return properties;
        }

        public void setProperties(Film properties) {
            this.properties = properties;
        }
    }
}

