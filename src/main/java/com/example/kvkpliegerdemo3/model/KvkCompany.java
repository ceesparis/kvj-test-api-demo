package com.example.kvkpliegerdemo3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KvkCompany {
    @JsonProperty("kvkNummer")
    private String kvkNumber;
    @JsonProperty("handelsnaam")
    private String name;
    @JsonProperty("plaats")
    private String location;
}
