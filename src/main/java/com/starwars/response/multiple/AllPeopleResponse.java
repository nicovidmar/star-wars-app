package com.starwars.response.multiple;

import java.util.List;

public class AllPeopleResponse {
    private List<PersonSummary> results;
    private String next;
    private String previous;

    public List<PersonSummary> getResults() {
        return results;
    }

    public void setResults(List<PersonSummary> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public static class PersonSummary {
        private String uid;
        private String name;
        private String url;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
