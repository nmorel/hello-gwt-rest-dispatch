package com.github.nmorel.hello.gwtp.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreetingRequest {

    private String name;

    public GreetingRequest( @JsonProperty("name") String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
