package com.starwars.response.multiple;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllStarshipsResponse {
    private List<StarshipSummary> results;
    private String next;
    private String previous;
    
    @JsonProperty("total_pages")
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<StarshipSummary> getResults() {
        return results;
    }

    public void setResults(List<StarshipSummary> results) {
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

    public static class StarshipSummary {
        private String uid;
        private String name;
        private String url;

        public StarshipSummary(String uid, String name, String url) {
            this.uid = uid;
            this.name = name;
            this.url = url;
        }

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
