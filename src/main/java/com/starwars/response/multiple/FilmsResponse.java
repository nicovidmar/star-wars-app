package com.starwars.response.multiple;

import java.util.List;
import com.starwars.entity.Film;

public class FilmsResponse {
    private String message;
    private List<FilmsResult> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<FilmsResult> getResult() {
        return result;
    }

    public void setResult(List<FilmsResult> result) {
        this.result = result;
    }

    public static class FilmsResult {
        private Film properties;

        public Film getProperties() {
            return properties;
        }

        public void setProperties(Film properties) {
            this.properties = properties;
        }
    }
}
