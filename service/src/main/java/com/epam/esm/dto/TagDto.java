package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name"})
public class TagDto extends RepresentationModel<TagDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Length(min = 10)
    private String name;

    public TagDto(String name) {
        this.name = name;
    }
}
