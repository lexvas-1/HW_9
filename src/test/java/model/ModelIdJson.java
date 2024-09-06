package model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ModelIdJson {
    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("dob")
    private final String dob;

    public ModelIdJson() {
        id = "";
        name = "";
        dob = "";
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

