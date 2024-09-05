package model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ModelIdJson {
    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("dob")
    private final String dob;

    public ModelIdJson(String id, String name, String dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public String getDob() {
        return dob;
    }
}

