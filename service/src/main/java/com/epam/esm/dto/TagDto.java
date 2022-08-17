package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@ToString
@Component
public class TagDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    private String name;

    public TagDto() {
    }

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
