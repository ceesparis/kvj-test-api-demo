package com.example.kvkpliegerdemo3.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KvkSearchResponse {
    @JsonProperty("pagina")
    int page;
    @JsonProperty("aantal")
    int amount;
    @JsonProperty("totaal")
    int total;
    @JsonProperty("resultaten")
    KvkCompany[] results;

}
