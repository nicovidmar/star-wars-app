package com.starwars.response.single;

import com.starwars.entity.Person;

public class PersonResponse {
    private String message;
    private Result result;

    public static class Result {
        private String description;
        private String uid;
        private Person properties;

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

    public PersonResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}