package com.starwars.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Starship {
    private String name;
    private String model;
    @JsonProperty("starship_class")
    private String starshipClass;
    private String manufacturer;
    @JsonProperty("cost_in_credits")
    private String costInCredits;
    private String length;
    private String crew;
    private String passengers;
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;
    @JsonProperty("hyperdrive_rating")
    private String hyperdriveRating;
    private String MGLT;
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;
    private String consumables;
    private List<String> films;
    private List<String> pilots;
    private String url;
    private String created;
    private String edited;

    public Starship() {
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getStarshipClass() {
        return starshipClass;
    }
    public void setStarshipClass(String starshipClass) {
        this.starshipClass = starshipClass;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getCostInCredits() {
        return costInCredits;
    }
    public void setCostInCredits(String costInCredits) {
        this.costInCredits = costInCredits;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getCrew() {
        return crew;
    }
    public void setCrew(String crew) {
        this.crew = crew;
    }
    public String getPassengers() {
        return passengers;
    }
    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }
    public String getMaxAtmospheringSpeed() {
        return maxAtmospheringSpeed;
    }
    public void setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
        this.maxAtmospheringSpeed = maxAtmospheringSpeed;
    }
    public String getHyperdriveRating() {
        return hyperdriveRating;
    }
    public void setHyperdriveRating(String hyperdriveRating) {
        this.hyperdriveRating = hyperdriveRating;
    }
    public String getMGLT() {
        return MGLT;
    }
    public void setMGLT(String MGLT) {
        this.MGLT = MGLT;
    }
    public String getCargoCapacity() {
        return cargoCapacity;
    }
    public void setCargoCapacity(String cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }
    public String getConsumables() {
        return consumables;
    }
    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }
    public List<String> getFilms() {
        return films;
    }
    public void setFilms(List<String> films) {
        this.films = films;
    }
    public List<String> getPilots() {
        return pilots;
    }
    public void setPilots(List<String> pilots) {
        this.pilots = pilots;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getEdited() {
        return edited;
    }
    public void setEdited(String edited) {
        this.edited = edited;
    }

    

}
