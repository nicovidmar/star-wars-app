package com.starwars.response.multiple;

import java.util.List;
import com.starwars.entity.Person;

public class PeopleResponse {
    private String message;
    private List<Result> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        private String uid;
        private Person properties;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Person getProperties() {
            return properties;
        }

        public void setProperties(Person properties) {
            this.properties = properties;
        }
    }
}