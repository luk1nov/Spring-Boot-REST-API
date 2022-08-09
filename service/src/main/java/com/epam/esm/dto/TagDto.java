package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@ToString
@Component
public class TagDto {
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
